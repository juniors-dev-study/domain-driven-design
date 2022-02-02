import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("java-library")
    id("java-test-fixtures")
    kotlin("plugin.serialization") version "1.5.31"
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
        implementation("org.springframework.boot:spring-boot-starter-validation")

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

    apply(plugin = "io.spring.dependency-management")

    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.4")
        }
    }
}

project(":user-api") {
    dependencies {
        implementation(project(":submodules:commons"))

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.springfox:springfox-boot-starter:3.0.0")
        implementation("org.springframework.security:spring-security-test")
        implementation("org.springframework.boot:spring-boot-starter-mail")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("mysql:mysql-connector-java")
        testImplementation(testFixtures(project(":submodules:commons")))
    }
}

project(":article-api") {
    apply(plugin = "kotlinx-serialization")

    dependencies {
        implementation(project(":submodules:commons"))

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.springfox:springfox-boot-starter:3.0.0")
        implementation("org.springframework.security:spring-security-test")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("mysql:mysql-connector-java")
        testImplementation(testFixtures(project(":submodules:commons")))
    }
}

project(":front") {
    dependencies {
        // thymeleaf 설정
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
        implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")

        // gateway
        implementation("org.springframework.cloud:spring-cloud-starter-gateway")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
        implementation("org.springframework.session:spring-session-core")
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        // 수정사항 빠르게 확인하기 위해 사용
        // Settings( CMD + , ) > Build, Execution, Deployment > Compiler에서 Build project automatically 체크
        // Settings( CMD + , ) > Build > Gradle에서 Build and run using 항목을 IntelliJ IDEA로 변경
        // intellij registry에서 compiler.automake.allow.when.app.running 체크
        implementation("org.springframework.boot:spring-boot-devtools")
    }
}

project(":authentication") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        runtimeOnly("mysql:mysql-connector-java")
        runtimeOnly("com.h2database:h2")
        implementation("org.springframework.cloud:spring-cloud-security:2.2.5.RELEASE")
        implementation("org.springframework.cloud:spring-cloud-starter-oauth2:2.2.5.RELEASE")
    }
}

project(":submodules") {
    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }
}

project(":submodules:commons") {
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")
    dependencies {
        api("org.springframework.boot:spring-boot-starter-integration")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
        implementation("org.springframework.security:spring-security-oauth2-jose")
        // testFixtures
        testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
        testFixturesImplementation("org.springframework.security:spring-security-test")
    }

    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }
}
