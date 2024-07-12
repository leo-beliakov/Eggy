plugins {
    alias(libs.plugins.eggy.android.feature)
    alias(libs.plugins.eggy.compose.library)
    alias(libs.plugins.eggy.hilt)
}

android {
    namespace = "com.leoapps.welcome"
}

dependencies {
    implementation(libs.androidx.compose.constraintlayout)
}