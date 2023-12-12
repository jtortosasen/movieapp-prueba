plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlinx-serialization")
}

//val secretsProperties = Properties()
//file("secrets.properties").inputStream().use { secretsProperties.load(it) }

android {
    namespace = "com.example.data"
    compileSdk = 34

//    defaultConfig {
//        buildConfigField("String", "API_KEY", "\"${secretsProperties["API_KEY"]}\"")
//        buildConfigField("String", "BEARER_TOKEN", "\"${secretsProperties["BEARER_TOKEN"]}\"")
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain"))

    //Ktor
    implementation("io.ktor:ktor-client-core:2.3.2")
    implementation("io.ktor:ktor-client-okhttp:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    testImplementation("io.ktor:ktor-client-mock:2.3.2")

    // Koin
    implementation("io.insert-koin:koin-core:3.4.2")
    implementation("io.insert-koin:koin-android:3.4.2")
    implementation("io.insert-koin:koin-androidx-compose:3.4.2")

    //Arrow
    implementation(platform("io.arrow-kt:arrow-stack:1.2.0-RC"))
    implementation("io.arrow-kt:arrow-core")

    testImplementation("junit:junit:4.13.2")
}