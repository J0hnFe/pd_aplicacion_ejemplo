plugins {
    id("java")
    id("io.freefair.lombok") version "8.13.1"
    id("io.quarkus") version "3.22.2"
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

val quarkusVersion = "3.22.2"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))

    //CDI
    implementation("io.quarkus:quarkus-arc")

    //REST
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")

    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")

    //JPA
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    implementation("org.modelmapper:modelmapper:3.2.3")

    //Service Discovery
//    implementation("io.quarkus:quarkus-smallrye-stork")
//    implementation("io.smallrye.stork:stork-service-discovery-static-list")

    implementation("io.quarkus:quarkus-smallrye-stork")
    implementation("io.smallrye.stork:stork-service-discovery-consul")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")

    // Fault Tolerance (Resiliencia)
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    // Telemetria: metricas
    // Metricas se exponen en: http://localhost:8080/q/metrics
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-jackson")

    // Health
    implementation("io.quarkus:quarkus-smallrye-health")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}