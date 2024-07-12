plugins {
    alias(libs.plugins.eggy.android.feature)
    alias(libs.plugins.eggy.compose.library)
    alias(libs.plugins.eggy.hilt)
}

android {
    namespace = "com.leoapps.welcome"
}

dependencies {
    implementation(project(":common:vibration"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)


    implementation(libs.androidx.compose.constraintlayout)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}