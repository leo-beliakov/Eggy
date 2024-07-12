plugins {
    alias(libs.plugins.eggy.android.library)
    alias(libs.plugins.eggy.compose.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.kotlin.compose.compiler)
    kotlin("kapt")
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.leoapps.setup"
}

dependencies {
    implementation(project(":common:base"))
    implementation(project(":common:base-ui"))
    implementation(project(":common:vibration"))
    implementation(project(":common:shared-res"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Serialization
    implementation(libs.jetbrains.kotlin.serialization)

    //Compose
    implementation(libs.androidx.compose.constraintlayout)
    implementation(libs.konfetti.compose)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Hilt DI
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}