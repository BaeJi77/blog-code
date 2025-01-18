plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.example.baeji"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.cloudevents:cloudevents-json-jackson:4.0.1")
    implementation("io.cloudevents:cloudevents-core:4.0.1")
    implementation("io.cloudevents:cloudevents-kafka:4.0.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
