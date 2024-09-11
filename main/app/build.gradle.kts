plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.android.sdk.exec"
    compileSdk = 34
    buildToolsVersion = "34.0.4"
    ndkVersion = "26.1.10909125"
    
    androidResources {
        noCompress.add(".so")
    }
    sourceSets {
        getByName("main") {
            jniLibs {
                srcDirs("libs")
            }
        }
    }
    
    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    defaultConfig {
        applicationId = "com.android.sdk.exec"
        minSdk = 23
        targetSdk = 35
        versionCode = 11
        versionName = "v0.0.36"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
        
        externalNativeBuild {
            cmake {
                abiFilters("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
                
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
        create("library") {
        // 签名路径，签名文件，.bks 或 .jks
            storeFile = file("keystore/library.keystore")
            storePassword = ""
            keyAlias = ""
            keyPassword = ""
            
            enableV1Signing = true
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
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("library")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("library")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        aidl = true
        buildConfig = true
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
}

tasks
    .withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>()
    .configureEach {
        compilerOptions
            .jvmTarget
            .set(
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
            )
    }

dependencies {
    val lifecycle_version = "2.8.5"
    
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("androidx.fragment:fragment-ktx:1.8.3")
    implementation("com.google.guava:guava:33.3.0-jre")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("androidx.compose.ui:ui:1.7.0")
}
