plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenLocal()
    maven { url = uri("https://repo.maven.apache.org/maven2/") }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks.shadowJar {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Main-Class" to "me.txmc.rtmixin.loader.Main",
        )
    }
    includeEmptyDirs = false
    minimize()
}

group = "me.l2x9"
version = "1.0-SNAPSHOT"
description = "L2X9RebootCore"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}