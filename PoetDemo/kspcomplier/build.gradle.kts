val kspVersion: String by project
plugins {
    // 这其实就是个java 的plugin
    kotlin("jvm")
}

group = "com.yxhuang.ksp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":annotation"))
    implementation("com.squareup:kotlinpoet:1.9.0")

    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")

//    implementation("com.google.auto.service:auto-service-annotations:1.0-rc7")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}
