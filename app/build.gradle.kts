plugins {
    alias(libs.plugins.eggy.android.application)
    alias(libs.plugins.eggy.compose.application)
    alias(libs.plugins.eggy.hilt)
}

android {
    namespace = "com.leoapps.eggy"

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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