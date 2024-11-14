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
                add("ksp", library("hilt.compiler"))
                add("implementation", library("hilt.core"))
            }

            /** Add support for Android modules, based on [AndroidBasePlugin] */
            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("dagger.hilt.android.plugin")
                dependencies {
                    add("implementation", library("hilt.compose"))
                    add("implementation", library("hilt.android"))

                    // Tests
                    add("androidTestImplementation", library("hilt.android.test"))
                    add("kspAndroidTest", library("hilt.compiler.test"))
                }
            }
        }
    }
}