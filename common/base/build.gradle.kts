plugins {
    alias(libs.plugins.eggy.android.library)
}

android {
    namespace = "com.leoapps.base"
}

dependencies {
    implementation(libs.androidx.activity.ktx)
}