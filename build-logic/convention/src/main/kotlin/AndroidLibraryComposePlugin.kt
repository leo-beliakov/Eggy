import com.android.build.api.dsl.LibraryExtension
import extensions.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Configures Compose for an Android library project.
 */
class AndroidLibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            configureCompose(extensions.getByType<LibraryExtension>())
        }
    }
}
