import com.android.build.gradle.api.AndroidBasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                add("ksp", libraries.findLibrary("hilt.compiler").get())
                add("implementation", libraries.findLibrary("hilt.core").get())
            }

            /** Add support for Android modules, based on [AndroidBasePlugin] */
            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("dagger.hilt.android.plugin")
                dependencies {
                    add("implementation", libraries.findLibrary("hilt.compose").get())
                    add("implementation", libraries.findLibrary("hilt.android").get())
                }
            }
        }
    }
}