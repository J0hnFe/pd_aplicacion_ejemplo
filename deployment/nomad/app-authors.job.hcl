job "app-authors" {
  datacenters = ["dc1"]
  type = "service"
  group "app-authors" {
    count = 2
    network {
      port "http" {
      }
    }
    task "app-authors" {
      driver = "java"
      config {
        jar_path = "c:/distribuida2525/app-authors/quarkus-run.jar"
      }
      env {
        QUARKUS_HTTP_PORT = "${NOMAD_PORT_http}"
      }
      resources {
        cpu = 2000 # 5000 MHz
        memory = 1024 # 1 GB
      }
      service {
        provider = "nomad"
        name     = "app-authors-http"
        port     = "http"
        tags = ["quarkus-app"]
      }
    }
  }
}