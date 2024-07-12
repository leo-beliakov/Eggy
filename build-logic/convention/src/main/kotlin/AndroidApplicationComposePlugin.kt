import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                buildFeatures {
                    compose = true
                }

                dependencies {
                    val bom = libraries.findLibrary("androidx-compose-bom").get()
                    add("implementation", platform(bom))
                    add("androidTestImplementation", platform(bom))
                    add(
                        "implementation",
                        libraries.findLibrary("androidx-ui-tooling-preview").get()
                    )
                }
            }
        }
    }
}