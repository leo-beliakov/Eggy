import com.android.build.gradle.api.AndroidBasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configures Hilt for a given project.
 *
 * This plugin applies the Hilt and KSP plugins and adds the required dependencies
 * for Hilt and Hilt Compose.
 */
class HiltPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                add("ksp", library("hilt.compiler").get())
                add("implementation", library("hilt.core").get())
            }

            /** Add support for Android modules, based on [AndroidBasePlugin] */
            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("dagger.hilt.android.plugin")
                dependencies {
                    add("implementation", library("hilt.compose").get())
                    add("implementation", library("hilt.android").get())

                    // Tests
                    add("androidTestImplementation", library("hilt.android.test").get())
                    add("kspAndroidTest", library("hilt.compiler.test").get())
                }
            }
        }
    }
}