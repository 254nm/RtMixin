# RtMixin is a JavaAgent that uses instrumentation to modify methods at runtime with a simple annotation system

## How to depend on (Gradle KTS)

### Plugins block 
```kotlin
plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
```

### Repositories block
```kotlin
repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.txmc.me/releases") }
}
```

### Dependencies block
```kotlin
dependencies {
    implementation("me.txmc:rtmixin:1.0-BETA")
    if (JavaVersion.current() == JavaVersion.VERSION_1_8) {
        compileOnly(files(org.gradle.internal.jvm.Jvm.current().toolsJar))
    }
}
```

### jar / shadowJar block
It is recommended to use shadowJar in order to include RtMixin in the final jar
```kotlin
tasks.shadowJar {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Main-Class" to "your.main.class",
            "Premain-Class" to "me.txmc.rtmixin.jagent.AgentMain",
            "Agent-Class" to "me.txmc.rtmixin.jagent.AgentMain",
            "Can-Redefine-Classes" to "true",
            "Can-Retransform-Classes" to "true",
            "Can-Set-Native-Method-Prefix" to "true"
        )
    }
    includeEmptyDirs = false
    minimize()
}
```