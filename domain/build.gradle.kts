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
dependencies {
    implementation("io.ktor:ktor-client-core:2.3.2")

    //Arrow
    implementation(platform("io.arrow-kt:arrow-stack:1.2.0-RC"))
    implementation("io.arrow-kt:arrow-core")
}