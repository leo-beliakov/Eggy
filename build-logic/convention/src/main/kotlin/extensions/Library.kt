import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Retrieves a library from the project's version catalog by the library's name.
 */
fun Project.library(name: String) = extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findLibrary(name)
    .get()