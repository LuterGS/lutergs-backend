import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.20"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "dev.lutergs"
version = "0.0.4"
java.sourceCompatibility = JavaVersion.VERSION_17

val springBootVersion = "3.1.1"
val springCloudAwsVersion = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:${springBootVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // mongoDB reactive
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:${springBootVersion}")

    // MD5 hashing
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")

    // AWS
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:${springCloudAwsVersion}"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")

    // JWT
    implementation("com.nimbusds:nimbus-jose-jwt:9.31")
    // TODO : jjwt 1.0 부터는 JWE 를 사용하므로, 확인 후 변경할 것
    // implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    // implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    // implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

graalvmNative {
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(17))
                vendor.set(JvmVendorSpec.matching("GraalVM"))
            })
        }
    }
}
