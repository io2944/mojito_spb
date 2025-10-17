# Étape 1 : build Maven
FROM eclipse-temurin:25-jdk AS build

# Installer Maven
RUN apt-get update && apt-get install -y maven

# Créer le dossier de travail
WORKDIR /app

# Copier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Compiler et créer le JAR
RUN mvn clean package -DskipTests

# Vérifier le contenu de target (debug)
RUN ls -l /app/target

# Étape 2 : image finale
FROM eclipse-temurin:25-jdk
WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", "-jar", "app.jar"]
