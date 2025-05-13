plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.finance"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    /* web */
    implementation("org.springframework.boot:spring-boot-starter-web")

    /* batch */
    implementation("org.springframework.boot:spring-boot-starter-batch")

    /* exposed */
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.61.0")

    /* database */
    implementation("com.h2database:h2")
    implementation("com.mysql:mysql-connector-j")

    /* kotlin */
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    /* jackson */
    implementation("com.fasterxml.jackson.core:jackson-databind")

    /* lombok */
    compileOnly("org.projectlombok:lombok")

    /* junit */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.batch:spring-batch-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
