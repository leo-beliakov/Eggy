plugins {
    alias(libs.plugins.eggy.android.library)
    alias(libs.plugins.eggy.compose.library)
    alias(libs.plugins.eggy.hilt)
}

android {
    namespace = "com.leoapps.ui_test"
}

dependencies {
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.test)
}