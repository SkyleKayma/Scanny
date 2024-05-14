import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        // Gradle
        classpath(libs.plugin.gradle)

        // Kotlin
        classpath(libs.plugin.kotlin)

        // KSP
        classpath(libs.plugin.ksp)

        // Hilt
        classpath(libs.plugin.hilt)

        // Google services
        classpath(libs.plugin.googleServices)

        // Crashlytics
        classpath(libs.plugin.firebaseCrashlytics)

        // Firebase App Distribution
        classpath(libs.plugin.firebaseAppdistribution)

        // Easy Launcher
        classpath(libs.plugin.easylauncher)
    }
}

subprojects {
    tasks {
        withType(JavaCompile::class.java).configureEach {
            sourceCompatibility = JavaVersion.VERSION_1_8.name
            targetCompatibility = JavaVersion.VERSION_1_8.name
        }

        withType(KotlinCompile::class.java).configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
}