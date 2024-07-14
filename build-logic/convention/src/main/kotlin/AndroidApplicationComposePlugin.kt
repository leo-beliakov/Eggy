import com.android.build.api.dsl.ApplicationExtension
import extensions.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Configures Compose for an Android application project.
 */
class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            configureCompose(extensions.getByType<ApplicationExtension>())
        }
    }
}
