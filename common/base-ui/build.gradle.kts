plugins {
    alias(libs.plugins.eggy.android.library)
    alias(libs.plugins.eggy.compose.library)
}

android {
    namespace = "com.leoapps.base_ui"
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.text)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
}