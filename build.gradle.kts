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

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // arcus
    implementation ("com.navercorp.arcus:arcus-java-client:1.13.2")
    implementation (group ="org.apache.zookeeper", name = "zookeeper", version = "3.5.9") // 설정 안하면 initZookeeperClient 에러남
}

tasks.withType<Test> {
    useJUnitPlatform()
}
