plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.services)
}

android.buildFeatures.buildConfig = true
val GITHUB_CLIENT_ID: String by project
val GITHUB_CLIENT_SECRET: String by project
val GOOGLE_ID_CLIENT: String by project

android {
    namespace = "mx.uv.fiee.iinf.tyam.firebaseauthapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "mx.uv.fiee.iinf.tyam.firebaseauthapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "GITHUB_CLIENT_ID", GITHUB_CLIENT_ID)
        buildConfigField("String", "GITHUB_CLIENT_SECRET", GITHUB_CLIENT_SECRET)
        buildConfigField("String", "GOOGLE_ID_CLIENT", GOOGLE_ID_CLIENT)
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

dependencies {
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.play.services.auth)
    implementation (platform(libs.firebase.bom))
    implementation (libs.firebase.core)
    implementation (libs.firebase.auth)
    implementation(libs.gson)
    implementation(libs.okhttp)

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}