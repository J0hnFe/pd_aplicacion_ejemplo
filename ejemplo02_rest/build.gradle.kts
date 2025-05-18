plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    //API CDI, JAX-RS, JSON-B
    //solo para compilar y para que no nos de errores de compilacion
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")

    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
}
