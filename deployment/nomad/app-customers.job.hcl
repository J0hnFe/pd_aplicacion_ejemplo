job "app-customers" {
  datacenters = ["dc1"]
  type = "service"
  group "app-customers" {
    count = 3
    network {
      port "http" {
      }
    }
    task "app-customers" {
      driver = "java"
      config {
        jar_path = "c:/distribuida2525/app-customers/quarkus-run.jar"
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
        name     = "app-customers-http"
        port     = "http"
        tags = ["quarkus-app"]
      }
    }
  }
}