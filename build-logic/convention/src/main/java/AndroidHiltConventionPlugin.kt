import com.winphyoethu.entain.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            dependencies {
                add("ksp", libs.findLibrary("hilt-compiler").get())
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                dependencies {
                    add("implementation", libs.findLibrary("hilt-core").get())
                }
            }

            pluginManager.withPlugin("com.android.base") {
                dependencies {
                    pluginManager.apply("dagger.hilt.android.plugin")
                    add("implementation", libs.findLibrary("hilt-android").get())
                }
            }
        }
    }

}