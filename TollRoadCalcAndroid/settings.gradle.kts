pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://artifactory.2gis.dev/sdk-maven-release")
        maven("https://jitpack.io")
    }
}

rootProject.name = "TollRoadCalcAndroid"
include(":app")
include(":feature:onboarding:api")
include(":feature:onboarding:impl")
include(":core")
include(":feature:home:api")
include(":feature:home:impl")
include(":feature:profile:api")
include(":feature:profile:impl")
include(":feature:auth:api")
include(":feature:auth:impl")
