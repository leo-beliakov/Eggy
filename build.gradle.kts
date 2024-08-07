// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.ksp) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.distribution) apply false
}

//Configure compose reports and stability config:
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
            freeCompilerArgs += listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=${rootDir.absolutePath}/compose_compiler_config.conf"
            )
        }
    }
}

// Create an aggregated test report
tasks.register<TestReport>("aggregateTestReports") {
    destinationDir = file("${rootDir.absolutePath}/app/build/reports/tests/aggregated")
    subprojects.forEach { subproject ->
        subproject.tasks.withType<Test> {
            ignoreFailures = true
            reportOn(this)
        }
    }
}