plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "xyn.xyn.xyn"
    compileSdk = 34
    buildToolsVersion = "34.0.4"
    ndkVersion = "26.1.10909125"
    
    androidResources {
        noCompress.add(".so")
    }
    
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    defaultConfig {
        applicationId = "xyn.xyn.xyn"
        minSdk = 23
        targetSdk = 34
        versionCode = 18
        versionName = "v2.1.1"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
        
        externalNativeBuild {
            cmake {
                abiFilters("arm64-v8a", "armeabi-v7a", "x86_64")
                
            }
        }
    }
    
    externalNativeBuild {
        cmake {
            // CMakeLists.txt 
            path("src/main/cpp/CMakeLists.txt")
            
        }
        
    }
    
    signingConfigs {
        create("apply-md5") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("apply/apply-md5.keystore")
        
            storePassword = ""
        
            keyAlias = "null"
        
            keyPassword = ""
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
        create("apply-sha1") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("apply/apply-sha1.keystore")
        
            storePassword = ""
        
            keyAlias = "null"
        
            keyPassword = ""
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
        create("apply-sha224") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("apply/apply-sha224.keystore")
        
            storePassword = ""
        
            keyAlias = "null"
        
            keyPassword = ""
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
        create("apply-sha256") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("apply/apply-sha256.keystore")
        
            storePassword = ""
        
            keyAlias = "null"
        
            keyPassword = ""
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
        create("apply-sha384") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("apply/apply-sha384.keystore")
        
            storePassword = ""
        
            keyAlias = "null"
        
            keyPassword = ""
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
        create("apply-sha512") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("apply/apply-sha512.keystore")
        
            storePassword = ""
        
            keyAlias = "null"
        
            keyPassword = ""
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
            signingConfig = signingConfigs.getByName("apply-sha384")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles("proguard-rules-debug.pro")
            signingConfig = signingConfigs.getByName("apply-sha384")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        aidl = true
        buildConfig = true
    }
    
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}

dependencies {
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("androidx.core:core-ktx:1.8.0")
}
