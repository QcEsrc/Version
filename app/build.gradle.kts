plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.os.version"
    compileSdk = 34
    buildToolsVersion = "34.0.4"
    ndkVersion = "26.1.10909125"
    
    androidResources {
        noCompress.add(".so")
    }
    
    defaultConfig {
        applicationId = "com.os.version"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "v0.0.1"
        
        vectorDrawables { 
            useSupportLibrary = true
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
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    signingConfigs {
        create("win") {
            storeFile = file("keystore/win.keystore")
            storePassword = findProperty("KEYSTORE_PASSWORD") as String
            keyAlias = findProperty("KEY_ALIAS") as String
            keyPassword = findProperty("KEY_PASSWORD") as String
            
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("win")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("win")
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
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-ktx:1.9.2")
}
