plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.win.x86.x64"
    compileSdk = 34
    buildToolsVersion = "34.0.4"
    ndkVersion = "26.1.10909125"
    
    androidResources {
        noCompress.add(".so")
    }
    
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
    val media3_version = "1.4.1"
    val graphics_version = "1.0.1"
    val sheets = "1.3.0"
    val accompanist = "0.36.0"
    val room = "2.6.1"
    val retrofit = "2.11.0"
    val composeBom = platform("androidx.compose:compose-bom:2024.09.01")
    implementation(composeBom)
    testImplementation(composeBom)
    androidTestImplementation(composeBom)
    ksp(composeBom)
    annotationProcessor(composeBom)
    runtimeOnly(composeBom)
    debugImplementation(composeBom)
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.dagger:dagger-compiler:2.51.1")
    ksp("com.google.dagger:dagger-compiler:2.51.1")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.9")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.core:core-ktx")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.runtime:runtime")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.2")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    runtimeOnly("androidx.compose.material:material-icons-extended")
    implementation("com.aayushatharva.brotli4j:brotli4j:1.17.0")
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    implementation("androidx.media3:media3-datasource-okhttp:$media3_version")
    implementation("androidx.media3:media3-ui:$media3_version")
    implementation("androidx.javascriptengine:javascriptengine:1.0.0-beta01")
    implementation("androidx.graphics:graphics-core:$graphics_version")
    implementation("androidx.graphics:graphics-path:$graphics_version")
    implementation("androidx.graphics:graphics-shapes:$graphics_version")
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:$sheets")
    implementation("com.maxkeppeler.sheets-compose-dialogs:list:$sheets")
    implementation("com.maxkeppeler.sheets-compose-dialogs:input:$sheets")
    runtimeOnly("com.google.accompanist:accompanist-webview:$accompanist")
    runtimeOnly("com.google.accompanist:accompanist-navigation-animation:$accompanist")
    runtimeOnly("com.google.accompanist:accompanist-permissions:$accompanist")
    runtimeOnly("com.google.accompanist:accompanist-flowlayout:$accompanist")
    runtimeOnly("com.google.accompanist:accompanist-systemuicontroller:$accompanist")
    implementation("com.google.accompanist:accompanist-themeadapter-material3:$accompanist")
    runtimeOnly("com.google.accompanist:accompanist-pager:$accompanist")
    implementation("androidx.room:room-runtime:$room")
    annotationProcessor("androidx.room:room-compiler:$room")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.squareup.okio:okio:3.9.1")
    implementation("com.android.tools.smali:smali-dexlib2:3.0.8")
    runtimeOnly("com.android.tools.ddms:ddmlib:31.6.0")
}
