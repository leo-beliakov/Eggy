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



//                add("implementation", libraries.findLibrary("androidx.hilt.navigation.compose").get())
//                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
//                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
//                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
//                add("androidTestImplementation", libs.findLibrary("androidx.lifecycle.runtimeTesting").get())
            }
        }
    }
}