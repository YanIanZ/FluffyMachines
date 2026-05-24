plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0"
}

group = "io.ncbpfluffybear"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = 25
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    compileOnly("com.github.slimefun:Slimefun:4.9-UNOFFICIAL")
    compileOnly("com.gmail.nossr50.mcMMO:mcMMO:2.2.029") {
        exclude("*", "*")
    }
    // lombok removed - JDK 25 incompatible
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.github.Slimefun-Addon-Community:extrautils:73e76ac06c")
}

tasks.shadowJar {
    archiveClassifier = ""
    relocate("io.papermc.lib", "io.ncbpfluffybear.fluffymachines.libraries.paperlib")
    relocate("org.bstats", "io.ncbpfluffybear.shaded.bstats")
    relocate("com.github.Slimefun-Addon-Community.extrautils", "io.ncbpfluffybear.shaded.extrautils")
    exclude("META-INF/**")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(mapOf("project" to project))
    }
}
