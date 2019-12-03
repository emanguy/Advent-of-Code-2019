plugins {
    kotlin("jvm") version "1.3.60"
}

group = "io.cloudtamer"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testRuntimeClasspath("org.slf4j:slf4j-simple:1.7.29")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}