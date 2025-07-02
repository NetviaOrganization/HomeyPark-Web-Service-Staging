# Imagen base oficial de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos necesarios primero (para aprovechar cache)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Da permisos al wrapper y descarga dependencias
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copia el resto del proyecto
COPY src ./src

# Compila el proyecto
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080 (usado por Spring Boot)
EXPOSE 8080

# Ejecuta la aplicación y permite que Spring use las variables de entorno inyectadas en tiempo de ejecución
CMD ["java", "-jar", "target/homeypark-0.0.1-SNAPSHOT.jar"]
