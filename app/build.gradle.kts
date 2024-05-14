import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.google.gms.googleservices.GoogleServicesTask
import java.io.FileInputStream
import java.io.FileWriter
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
    id("com.starter.easylauncher")
}

// Keystore
val keystorePropertiesFile = rootProject.file("private/keys/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

// Version
val versionPropertiesFile = rootProject.file("app/version.properties")
val versionProperties = Properties()
versionProperties.load(FileInputStream(versionPropertiesFile))

val versionCode = versionProperties.getProperty("versionCode").toInt()

android {
    namespace = "fr.skyle.scanny"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "fr.skyle.scanny"

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = versionCode
        versionName = versionProperties.getProperty("versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk { abiFilters += setOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64") }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(keystoreProperties["debugStoreFile"].toString())
        }
        create("release") {
            storeFile = file(keystoreProperties["releaseStoreFile"].toString())
            storePassword = keystoreProperties["passwordRelease"].toString()
            keyAlias = keystoreProperties["aliasRelease"].toString()
            keyPassword = keystoreProperties["passwordRelease"].toString()
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false

            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("long", "VERSION_CODE", "$versionCode")

//            firebaseAppDistribution {
//                artifactType = "APK"
//                groups = "Developer"
//                serviceCredentialsFile = file("./google-services-account.json")
//            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            buildConfigField("long", "VERSION_CODE", "$versionCode")

//            firebaseAppDistribution {
//                artifactType = "APK"
//                groups = "Developer"
//                serviceCredentialsFile = file("./google-services-account.json")
//            }
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

easylauncher {
    buildTypes.findByName("debug")?.apply {
        setFilters(grayRibbonFilter("DEBUG"))
    }
}

tasks.register<Copy>("clonePrivateFiles") {
    from("../private/firebase/google-services.json")
    into("./")
}

tasks.register("incrementBuildNumberAfterPublish") {
    doLast {
        println("Incrementing build number by 1 (current $versionCode)")
        val newCode = versionCode + 1
        versionProperties["versionCode"] = newCode.toString()
        versionProperties.store(FileWriter(versionPropertiesFile), null)
    }
}

project.afterEvaluate {
    tasks.named("processDebugGoogleServices").dependsOn("clonePrivateFiles")

    // I think there is an issue currently that forces to write this...
    tasks.named("mergeDebugJniLibFolders").dependsOn("clonePrivateFiles")
    tasks.named("mergeLibDexDebug").dependsOn("clonePrivateFiles")
    tasks.named("mergeExtDexDebug").dependsOn("clonePrivateFiles")
    tasks.named("mergeDebugShaders").dependsOn("clonePrivateFiles")
}

dependencies {
    // Android
    implementation(libs.bundles.android)

    // Lifecycle
    implementation(libs.bundles.androidx.lifecycle)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Timber
    implementation(libs.timber)

    // Accompanist
    implementation(libs.bundles.accompanist)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // Camera
    implementation(libs.bundles.camera)

    // ML Kit
    implementation(libs.mlkit)

    // Splash screen Api
    implementation(libs.splashscreen)

    // Datastore
    implementation(libs.bundles.datastore)

    // Tests
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.androidx.junit)
}