plugins {
    alias(libs.plugins.android.application)

}

android {
    namespace = "mx.uv.fiee.iinf.tyam.fragmentbasedapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "mx.uv.fiee.iinf.tyam.fragmentbasedapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        viewBinding = true
    }
}
configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}