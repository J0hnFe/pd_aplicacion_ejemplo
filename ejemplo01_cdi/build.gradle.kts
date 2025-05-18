plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jboss.weld.se:weld-se-core:6.0.2.Final")
}
//archivos source bueno invest
sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main") )
    }
}