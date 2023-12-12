plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.example.domain"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
