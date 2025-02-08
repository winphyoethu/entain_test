import com.winphyoethu.entain.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("entain.android.library")
                apply("entain.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("entain.android.lint")
            }

            dependencies {
                add("implementation", project(":core:designsystem"))

                add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())
                add("implementation", libs.findLibrary("androidx-navigation-compose").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-viewModelCompose").get())
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

            }
        }
    }

}