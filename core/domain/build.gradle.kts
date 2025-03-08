plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinx.serialization)
}

group = libs.versions.packageName.get() + ".domain"
version = libs.versions.versionCode.get().toInt()

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}
