val serviceName = project.path.removePrefix(":").substringBefore(":")
val javaVersion = 21
val lombokVersion = "1.18.34"
val dotenvVersion = "3.0.0"
val h2Version = "2.2.224"
val projectEncoding = "UTF-8"
val queryDslVersion = "5.1.0"
val mapstructVersion = "1.5.5.Final"

plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jetbrains.kotlin.jvm") version "1.7.22" apply false
}

group = "com.personal.marketnote.user.adapters"
version = "1.0.0"
description = "user service adapters"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

// 별도 소스 JAR 다운로드용 구성 (IDE가 소스 첨부 못할 때 수동 다운로드)
val redisSources by configurations.creating

repositories {
    mavenCentral()
}

dependencies {
    // module
    implementation(project(":common"))
    implementation(project(":user-service:user-application"))
    implementation(project(":user-service:user-domain"))

    // 🔹 Spring Boot 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA (데이터베이스 ORM)
    implementation("org.springframework.boot:spring-boot-starter-web") // Spring MVC (REST API 개발)
    implementation("org.springframework.boot:spring-boot-starter-validation") // Spring Validation
    implementation("org.springframework.boot:spring-boot-starter-security") // Spring Security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server") // OAuth 2.0 Resource server

    // Spring Data Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // session
    implementation("org.springframework.session:spring-session-data-redis")

    //querydsl 설정
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api:3.1.0")

    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // 🔹 Lombok 설정 (코드 자동 생성 도구)
    compileOnly("org.projectlombok:lombok:$lombokVersion") // 빌드 타임에만 필요한 라이브러리
    annotationProcessor("org.projectlombok:lombok:$lombokVersion") // 애너테이션 프로세서 활성화
    testCompileOnly("org.projectlombok:lombok:$lombokVersion") // 빌드 타임에만 필요한 라이브러리
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion") // 애너테이션 프로세서 활성화

    // JSON parser
    implementation("org.json:json:20240303")

    // Jackson Hibernate Module (Jakarta, Boot 3.x/Hibernate 6 호환)
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // Lombok과 MapStruct 통합 (Lombok이 먼저 처리되도록)
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // 🔹 테스트 관련 의존성
    testImplementation("org.springframework.boot:spring-boot-starter-test") // 테스트를 위한 기본 라이브러리
    testImplementation("org.springframework.security:spring-security-test") // Spring Security 테스트 지원
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // JUnit 테스트 런처
    testImplementation("org.awaitility:awaitility:4.2.0") // 비동기,스케줄링 테스트 지원
    // 🔹 추가 라이브러리
    // dotenv
    implementation("io.github.cdimascio:dotenv-java:$dotenvVersion")

    // H2 데이터베이스 (테스트용)
    runtimeOnly("com.h2database:h2:$h2Version")

    // PostgreSQL 드라이버
    runtimeOnly("org.postgresql:postgresql:42.7.4")

    // 빌드 정보를 위한 스프링 부트 액추에이터
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // 스웨거 API 문서 생성
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

    // Spring Batch
    implementation("org.springframework.boot:spring-boot-starter-batch")

    // Spring Batch 테스트 의존성
    testImplementation("org.springframework.batch:spring-batch-test")

    // HNSW 라이브러리
    implementation("com.github.jelmerk:hnswlib-core:1.2.1")

    // MIME 타입 체크를 위한 Apache Tika
    implementation("org.apache.tika:tika-core:3.1.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // spring-data-redis sources (IDE에서 소스 자동 첨부가 안 될 때 CLI로 받기 위함)
    redisSources("org.springframework.data:spring-data-redis:3.5.4:sources")
}

// ✅ 테스트 실행 시 JUnit 5 플랫폼 사용 설정
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
}

// ✅ UTF-8 인코딩 설정 (한글 깨짐 방지)
tasks.withType<JavaCompile>().configureEach {
    options.encoding = projectEncoding
}

tasks.register("printProjectName") {
    doLast {
        println(serviceName)
    }
}

tasks.register("printProjectVersion") {
    doLast {
        println(version)
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveBaseName.set(serviceName)
    enabled = true
}
tasks.named<Jar>("jar") {
    enabled = false
}

springBoot {
    mainClass.set("com.personal.marketnote.user.UserApplication")
    buildInfo()
}

// `./gradlew :user-service:user-adapters:downloadRedisSources` 실행 시 소스 JAR를 로컬 캐시에 받음
tasks.register("downloadRedisSources") {
    doLast {
        redisSources.resolve()
    }
}

tasks.register("prepareKotlinBuildScriptModel") {
    doLast {
        println("Dummy task for prepareKotlinBuildScriptModel executed")
    }
}
