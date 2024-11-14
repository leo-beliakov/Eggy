plugins {
    alias(libs.plugins.eggy.android.library)
    alias(libs.plugins.eggy.compose.library)
}

android {
    namespace = "com.leoapps.base_ui"
}

dependencies {
    implementation(libs.material3.window.size)
}