plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

    //API CDI, JAX-RS, JSON-B
    //implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")

    //implementation("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
    //CDI wel
    implementation("org.jboss.weld.se:weld-se-core:6.0.2.Final")
    implementation("org.jboss.weld.servlet:weld-servlet-core:6.0.2.Final")
    //jandex
    implementation("io.smallrye:jandex:3.2.7")
    //resteasy
    implementation("org.jboss.resteasy:resteasy-core:6.2.12.Final")
    implementation("org.jboss.resteasy:resteasy-undertow-cdi:6.2.12.Final")

}

sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main") )
    }
}