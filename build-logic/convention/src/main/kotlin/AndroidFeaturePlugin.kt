import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("leo.apps.android.library")
                apply("leo.apps.compose.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                // Base modules
                add("implementation", project(":common:base"))
                add("implementation", project(":common:base-ui"))
                add("implementation", project(":common:shared-res"))
                add("implementation", project(":common:vibration"))

                // Navigation
                add("implementation", libraries.findLibrary("jetbrains.kotlin.serialization").get())
                add("implementation", libraries.findLibrary("androidx.navigation.compose").get())

                // Tests
                add(
                    "androidTestImplementation",
                    libraries.findLibrary("androidx.ui.test.junit4").get()
                )
                add(
                    "androidTestImplementation",
                    libraries.findLibrary("androidx.navigation.test").get()
                )
                add("debugImplementation", libraries.findLibrary("androidx.ui.tooling").get())
                add("debugImplementation", libraries.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}