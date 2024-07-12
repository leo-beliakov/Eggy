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
        register("composeApplication") {
            id = "leo.apps.compose.application"
            implementationClass = "AndroidComposePlugin"
        }
        register("composeLibrary") {
            id = "leo.apps.compose.library"
            implementationClass = "AndroidLibraryComposePlugin"
        }
    }
}