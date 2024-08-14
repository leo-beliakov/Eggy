import com.google.firebase.appdistribution.gradle.firebaseAppDistribution

plugins {
    alias(libs.plugins.eggy.android.application)
    alias(libs.plugins.eggy.compose.application)
    alias(libs.plugins.eggy.hilt)
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "com.leoapps.eggy"

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        create("internal") {
            isMinifyEnabled = true
            isShrinkResources = true
            versionNameSuffix = "-INTERNAL"

            firebaseAppDistribution {
                artifactType = "APK"
                serviceCredentialsFile = "${rootDir}/firebase-distribution-key.json"
                groups = "QA"
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            firebaseAppDistribution {
                artifactType = "APK"
                serviceCredentialsFile = "${rootDir}/firebase-distribution-key.json"
                groups = "QA"
            }
        }
    }
}

dependencies {
    implementation(project(":common:base"))
    implementation(project(":common:base-ui"))
    implementation(project(":common:ui-test"))
    implementation(project(":common:shared-res"))
    implementation(project(":common:vibration"))
    implementation(project(":features:welcome"))
    implementation(project(":features:setup"))
    implementation(project(":features:progress"))
}