/*
plugins : Gradle에 어떤 기능을 사용할지 정의하는 부분
kotlin jvm 프로젝트
jvm 환경에서 Kotlin 컴파일 > 코틀린 코드를 .class 파일로 변환해서 Java처럼 실행하는 방식
 */
plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    application
}


/*
meta data: 패키지로 배포하거나 모듈 이름을 구분할 때 사용됨
 */
version = "1.0-SNAPSHOT"

/*
mavenCentral() : 의존성 라이브러리를 가져올 원격 저장소
    dependencies 블록에 선언한 내용을 여기서 다운로드
 */
repositories {
    mavenCentral()
}
/*
 testImplementation : 테스트 코드에 필요한 의존성
 */
dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // JPA & Hibernate
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")

    // PostgreSQL
    implementation("org.postgresql:postgresql:42.6.0")

    // HikariCP
    implementation("com.zaxxer:HikariCP:5.1.0")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // YAML
    implementation("org.yaml:snakeyaml:2.2")

    // Bcrypt
    implementation("at.favre.lib:bcrypt:0.9.0")

    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:2.3.3")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.ktor:ktor-client-cio:2.3.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-client-serialization-jvm:2.3.4")
    implementation("io.ktor:ktor-serialization-jackson:2.3.4")
    implementation("io.ktor:ktor-server-call-logging:2.3.4")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.3")

    // Hibernate JPA modelgen
    kapt("org.hibernate.orm:hibernate-jpamodelgen:6.4.4.Final")

    // Lombok (Kotlin에서 권장되지 않음)
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Logback
    implementation("org.slf4j:slf4j-simple:2.0.9")

    implementation("com.auth0:java-jwt:4.2.1")

    // mail
    implementation("com.sun.mail:javax.mail:1.6.2")

    // Test
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

/*
Gradle이 테스트를 실행할 때 JUnit5 플랫폼을 사용하겠다는 의미
@Test 등 사용 시 필요
 */
tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.example.wcody.MainKt")
}

/*
Kotlin 컴파일러가 사용할 JDK 버전을 명시하는 부분
 */
kotlin {
    jvmToolchain(17)
}

