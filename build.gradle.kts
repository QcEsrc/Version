plugins {
    val gradle_version = "8.6.0"
    val kotlin_version = "2.0.20"
    id("com.android.application") version "$gradle_version" apply false
    id("com.android.library") version "$gradle_version" apply false
    id("org.jetbrains.kotlin.android") version "$kotlin_version" apply false  
    id("org.jetbrains.kotlin.plugin.compose") version "$kotlin_version" apply false  
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}