import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures an Android library project.
 *
 * This plugin applies the necessary Android library and Kotlin plugins, configures the
 * Android library extension, sets compile options, and adds common test dependencies.
 */
class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 34

                defaultConfig.apply {
                    minSdk = 26
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
                    }
                }
                buildTypes {
                    create("internal")
                }
            }
            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                    freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
                }
            }

            dependencies {
                // Tests
                add("testImplementation", library("junit"))
                add("testImplementation", library("mockk.android"))
                add("androidTestImplementation", library("mockk.android"))
                add("androidTestImplementation", library("androidx.junit"))
                add("androidTestImplementation", library("androidx.espresso.core"))
            }
        }
    }
}