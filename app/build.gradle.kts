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
    
    packagingOptions {
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/AL2.0")
    }
    
    defaultConfig {
        applicationId = "xyn.xyn.xyn"
        minSdk = 26
        targetSdk = 34
        versionCode = 10
        versionName = "v1.8.86"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
        
        externalNativeBuild {
            cmake {
                abiFilters("arm64-v8a", "x86_64")
                
            }
        }
    }
    
    externalNativeBuild {
        cmake {
            // CMakeLists.txt 
            path("src/main/cpp/CMakeLists.txt")
            
        }
        
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles("proguard-rules-debug.pro")
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
    implementation("com.android.tools.ddms:ddmlib:31.5.1")
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")
}
