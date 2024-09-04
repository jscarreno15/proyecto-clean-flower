FROM adoptopenjdk:11-jdk-hotspot as build

# Directorio de trabajo dentro del contenedor
WORKDIR /app/

# Actualiza e instala solo los paquetes necesarios, quitando git
RUN apt-get update && apt-get install -y findutils maven && rm -rf /var/cache/apt/archives /var/lib/apt/lists/*

# Copia el directorio actual al directorio de trabajo en el contenedor
COPY . /app/backend-clean-flower

# Cambia el directorio de trabajo
WORKDIR /app/backend-clean-flower

# Descarga las dependencias del proyecto
RUN mvn dependency:resolve --fail-never 

# Compila y empaqueta el proyecto sin ejecutar pruebas
RUN mvn clean package

# Cambia el directorio de trabajo al directorio target
WORKDIR /app/backend-clean-flower/target

# Inicia una nueva etapa de construcción con una imagen base más ligera para ejecutar la aplicación
FROM adoptopenjdk:21-jre-hotspot

# Copia el archivo JAR generado en la etapa anterior al directorio de trabajo
COPY --from=build /app/backend-clean-flower/target/backend-1.0.0-.jar backend-1.0.0-.jar

# Expone el puerto en el que se ejecuta la aplicación Spring Boot
EXPOSE 4200

# Define el comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "backend-1.0.0-.jar"]

