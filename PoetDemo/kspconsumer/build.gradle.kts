plugins {
    id("com.google.devtools.ksp")
    kotlin("jvm")
}

repositories {
    mavenCentral()
    google()
    jcenter()
}



dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":annotation"))
    implementation(project(":kspcomplier"))
    ksp(project(":kspcomplier"))
}
