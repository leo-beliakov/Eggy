import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.gradle)
    implementation(libs.compose.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "leo.apps.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "leo.apps.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidFeature") {
            id = "leo.apps.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("composeApplication") {
            id = "leo.apps.compose.application"
            implementationClass = "AndroidApplicationComposePlugin"
        }
        register("composeLibrary") {
            id = "leo.apps.compose.library"
            implementationClass = "AndroidLibraryComposePlugin"
        }
        register("hilt") {
            id = "leo.apps.hilt"
            implementationClass = "HiltPlugin"
        }
    }
}