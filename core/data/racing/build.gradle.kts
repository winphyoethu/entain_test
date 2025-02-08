plugins {
    id("entain.android.library")
    id("entain.android.hilt")
}

android {
    namespace = "com.winphyoethu.entain.data.racing"

    defaultConfig {
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

    api(project(":core:common"))
    api(project(":core:model"))
    api(project(":core:network"))

    testImplementation(libs.junit)

}