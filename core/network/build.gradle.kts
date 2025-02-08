import java.util.Properties

plugins {
    id("entain.android.library")
    id("entain.android.hilt")
}

private val entainProperties = Properties().apply {
    load(rootProject.file("entain.properties").inputStream())
}

private val baseUrl = entainProperties.getProperty("BASE_URL")

android {
    namespace = "com.winphyoethu.entain.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", baseUrl)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    api(libs.retrofit.core)
    api(libs.moshi)
    api(libs.moshi.adapters)
    api(libs.moshi.kotlin)
    api(libs.moshi.converter)
    implementation(libs.okhttp.logging)
    testImplementation(libs.junit)

}