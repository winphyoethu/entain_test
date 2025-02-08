import com.winphyoethu.entain.convention.configureKotlinJvm
import com.winphyoethu.entain.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "entain.android.lint")

            configureKotlinJvm()
            dependencies {
                add("testImplementation", libs.findLibrary("kotlin-test").get())
            }
        }
    }
}