package extensions

import com.android.build.api.dsl.CommonExtension
import library
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configures the Compose settings & dependencies for a given project extension.
 */
fun Project.configureCompose(
    extension: CommonExtension<*, *, *, *, *, *>
) = with(extension) {
    buildFeatures {
        compose = true
    }

    dependencies {
        val bom = library("androidx-compose-bom")
        add("implementation", platform(bom))
        add("implementation", library("androidx-material3"))
        add("implementation", library("material3-window-size"))
        add("implementation", library("androidx-ui-tooling-preview"))

        // Tests
        add("androidTestImplementation", platform(bom))
        add("debugImplementation", library("androidx-ui-tooling"))
        add("androidTestImplementation", library("androidx-ui-test-junit4"))
        add("debugImplementation", library("androidx-ui-test-manifest"))
    }
}