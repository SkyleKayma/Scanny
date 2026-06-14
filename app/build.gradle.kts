import com.project.starter.easylauncher.filter.ChromeLikeFilter
import org.gradle.language.nativeplatform.internal.BuildType
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.appdistribution)
    alias(libs.plugins.easylauncher)
}

// Keystore
val keystorePropertiesFile = rootProject.file("private/keys/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "fr.skyle.scanny"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "fr.skyle.scanny"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 20
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        maybeCreate(BuildType.DEBUG.name).apply {
            storeFile = file(keystoreProperties["debugStoreFile"].toString())
        }
        maybeCreate(BuildType.RELEASE.name).apply {
            storeFile = file(keystoreProperties["releaseStoreFile"].toString())
            storePassword = keystoreProperties["passwordRelease"].toString()
            keyAlias = keystoreProperties["aliasRelease"].toString()
            keyPassword = keystoreProperties["passwordRelease"].toString()
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName(BuildType.DEBUG.name)

            firebaseAppDistribution {
                artifactType = "APK"
                groups = "Internal"
                serviceCredentialsFile = "./google-services-account.json"
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            signingConfig = signingConfigs.getByName(BuildType.RELEASE.name)

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            firebaseAppDistribution {
                artifactType = "APK"
                groups = "Internal"
                serviceCredentialsFile = "./google-services-account.json"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/DEPENDENCIES.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/{AL2.0,LGPL2.1}"
            )
        }
    }
}

easylauncher {
    buildTypes {
        register(BuildType.DEBUG.name).configure {
            filters(
                chromeLike(
                    label = "DEBUG",
                    gravity = ChromeLikeFilter.Gravity.BOTTOM,
                    ribbonColor = "#4F9E30",
                    overlayHeight = 0.2f,
                    textSizeRatio = 0.15f
                )
            )
        }
    }
}

tasks.register("clonePrivateFiles") {
    notCompatibleWithConfigurationCache("custom")
    doLast {
        copy {
            from("../private/firebase/google-services.json")
            into("./")
        }
    }
}

project.afterEvaluate {
    getTasks().getByName("preBuild").finalizedBy("clonePrivateFiles")
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.process)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.material.icons)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Compose navigation
    implementation(libs.bundles.navigation)

    // Timber
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.lifecycle.viewmodel.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    // Splash screen API
    implementation(libs.splashscreen)
}
