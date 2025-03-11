import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.composeReport) apply false
    alias(libs.plugins.androidLibrary) apply false
}

subprojects {
    apply<DetektPlugin>()
    apply<KtlintPlugin>()

    tasks.withType<Detekt>().configureEach {
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        source = fileTree("$projectDir/src/main")
        reportsDir = file("$projectDir/build/reports/detekt/")

        include("**/*.kt", "**/*.kts")
        exclude("**/resources/**", "**/build/**")

        buildUponDefaultConfig = false
        autoCorrect = true
        parallel = true

        reports {
            html.required = true
            xml.required = true
            txt.required = false
            sarif.required = false
            md.required = false
        }
    }

    configure<KtlintExtension> {
        ignoreFailures = false
        enableExperimentalRules = true
        reporters {
            reporter(ReporterType.HTML)
            reporter(ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}

allprojects {
    tasks.whenTaskAdded {
        if (name == "preBuild") {
            doLast {
                copy {
                    description = "Copy pre-commit script from hooks/"
                    group = "hooks"
                    from("$rootDir/hooks/pre-commit")
                    into("$rootDir/.git/hooks/")
                }
            }
        }
    }
}
