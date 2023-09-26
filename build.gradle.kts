plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
}

group = "com.jam2in"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // arcus
    implementation("com.jam2in.arcus:arcus-java-client:1.13.4")
    implementation("com.jam2in.arcus:arcus-spring:1.13.4")
    implementation(files("libs/arcus-app-common-2.0.1.jar"))

    implementation("net.sf.ehcache:ehcache-core:2.6.0")
    implementation("jline:jline:0.9.94")
    implementation("org.apache.zookeeper:zookeeper:3.5.9")
    implementation("io.netty:netty:3.10.6.Final")
    implementation("log4j:log4j:1.2.16")
//    implementation("org.slf4j:slf4j-api:1.7.25")
//    implementation("org.slf4j:slf4j-simple:1.7.25")
//    testImplementation("org.slf4j:slf4j-log4j12:1.7.25")
    implementation("org.aspectj:aspectjrt:1.7.3")
    implementation("org.aspectj:aspectjweaver:1.7.3")
    implementation("org.apache.commons:commons-lang3:3.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
