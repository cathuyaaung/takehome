# Spring Boot Continents REST API

This is a Spring Boot REST API that accepts a list of country codes and returns a list of country codes that are in the same continent as the input country codes. The API retrieves data from the [Countries GraphQL API](https://countries.trevorblades.com/graphql).

## Prerequisites

- JDK 17
- Docker (optional)

## Building the Application

### Using Gradle

To build the application, run the following command in the project's root directory:

```bash
./gradlew clean build
```

This command will build the project and generate a JAR file in the build/libs folder.

### Using Docker

To build a Docker image of the application, first build the project with Gradle as described above. Then, run the following command in the project's root directory:

```bash
docker build -t your-image-name .
```

Replace your-image-name with a suitable name for your Docker image.

## Running the Application

### Using Docker

To run the application in a Docker container, use the following command:

```bash
docker run -p 8080:8080 --name your-container-name your-image-name
```

Replace your-container-name with a suitable name for your Docker container.
Accessing the API

Once the application is running, you can access the API by sending a POST request with the country codes as the request body to the /api/continents endpoint. For example, use a tool like Postman or curl to send the request:

```bash
curl -X GET -H "Content-Type: application/json" http://localhost:8080/api/countries?codes=US,CA
```

This request will return a list of continents with the countries that match the input country codes and other countries in the same continent.