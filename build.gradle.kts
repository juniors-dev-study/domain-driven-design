import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

allprojects {
    group = "com.sns"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        // spring base
        implementation("org.springframework.boot:spring-boot-starter-web")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.mockk:mockk:${property("mockkVersion")}")
        testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
        testImplementation("org.junit.jupiter:junit-jupiter:${property("jupiterVersion")}")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":user-api") {
    dependencies {
        implementation(project(":submodules:commons"))

        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.springfox:springfox-boot-starter:3.0.0")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.security:spring-security-test")
        runtimeOnly("com.h2database:h2")
        runtimeOnly("mysql:mysql-connector-java")
    }
}

project(":front") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    }
}

project(":submodules") {
    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }
}

project(":submodules:commons") {
    dependencies {
        api("org.springframework.boot:spring-boot-starter-integration")
    }

    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }
}
