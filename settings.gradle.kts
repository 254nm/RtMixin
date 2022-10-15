rootProject.name = "RtMixin"

include(":RtMixin", ":JavaMVLoader", ":Example")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
