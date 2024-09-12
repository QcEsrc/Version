plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.win.x86.x64"
    compileSdk = 34
    buildToolsVersion = "34.0.4"
    
    defaultConfig {
        applicationId = "com.win.x86.x64"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "v0.0.1"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    signingConfigs {
        create("win") {
            storeFile = file("keystore/win.keystore")
            storePassword = findProperty("KEYSTORE_PASSWORD") as String
            keyAlias = findProperty("KEY_ALIAS") as String
            keyPassword = findProperty("KEY_PASSWORD") as String
            
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
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
        aidl = true
        buildConfig = true
        compose = true
    }
    
    composeCompiler {
        enableStrongSkippingMode = true

        reportsDestination = layout.buildDirectory.dir("compose_compiler")
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
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation(platform("androidx.compose:compose-bom:2024.09.01"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.runtime:runtime")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
