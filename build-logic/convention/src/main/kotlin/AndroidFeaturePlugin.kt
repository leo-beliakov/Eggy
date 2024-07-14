import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * Configures an Android feature module.
 *
 * This plugin applies necessary plugins
 * and adds common dependencies for base modules, navigation, and testing.
 */
class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("leo.apps.android.library")
                apply("leo.apps.compose.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "com.leoapps.ui_test.HiltTestRunner"
                }
            }

            dependencies {
                // Base modules
                add("implementation", project(":common:base"))
                add("implementation", project(":common:base-ui"))
                add("implementation", project(":common:ui-test"))
                add("implementation", project(":common:shared-res"))
                add("implementation", project(":common:vibration"))

                // Navigation
                add("implementation", library("jetbrains.kotlin.serialization").get())
                add("implementation", library("androidx.navigation.compose").get())

                // Tests
                add("androidTestImplementation", library("androidx.ui.test.junit4").get())
                add("androidTestImplementation", library("androidx.navigation.test").get())
                add("debugImplementation", library("androidx.ui.tooling").get())
                add("debugImplementation", library("androidx.ui.test.manifest").get())
            }
        }
    }
}