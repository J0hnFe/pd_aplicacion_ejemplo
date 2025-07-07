# docker build -t app-authors . ------------------> esto construye la imagen
# docker build -t j0hnfe/app-authors .   ------------------> esto construye la imagen y la etiqueta
# docker push j0hnfe/app-authors         ------------------> esto sube la imagen al repositorio

#desde donde este el dockercomposeyml
#docker compose up -d

FROM eclipse-temurin:21.0.7_6-jre-alpine-3.21

RUN mkdir /app
WORKDIR /app

COPY build/quarkus-app/lib ./lib
COPY build/quarkus-app/quarkus ./quarkus
COPY build/quarkus-app/app ./app
COPY build/quarkus-app/quarkus-run.jar app.jar

CMD ["java", "-jar", "app.jar"]