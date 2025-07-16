plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.4.3"
}

group = "org.demo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    compileOnly ("org.projectlombok:lombok") // Lombok untuk fase kompilasi saja
    runtimeOnly ("com.h2database:h2") // H2 database untuk runtime/pengembangan
    annotationProcessor ("org.projectlombok:lombok") // Lombok untuk proses anotasi (getter/setter otomatis)
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.test {
    useJUnitPlatform()
}