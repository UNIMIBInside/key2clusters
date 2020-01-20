# key2clusters
This service takes a keyword as input and returns the semantic clusters in which this keyword appears among the top thousand most significant.
The semantic clusters are Google Categories. 
Internally the thousands keyword for each category are stored either in Redis or withing a lightweight map. 
To store the keywords in Redis an instance of Redis with Bloom filters enabled must be available.

## Configuration
The behavior of the application can be configured in different ways. 
One way is to provide a *application.properties* configuration file. Another way is to set some environment variables.
On application.properties you can configure the Redis endpoint.
For example:

```
spring.profiles.active=MEMORY
keyword.cluster.filename=sample_keywords.csv
keyword.cluster.recreate-data-structure=true
```

In this specific example the service is instructed to work with in-memory data structure, to recreate the memory map at boot time (mandatory in this configuration) and to use the file *sample_keywords.csv* as input.


Another example:

```
spring.profiles.active=REDIS
keyword.cluster.filename=sample_keywords.csv

spring.redis.host=localhost
spring.redis.password=yourpassword
spring.redis.port=6379
        
keyword.cluster.recreate-data-structure=false
```

Here a Redis instance is used to store the clusters; moreover, the configuration entails that Redis already stored the clusters. Hence, there is no reason to load the data at startup.

## Usage
First, run your key2cluster service on your machine (see later). Then you can interact with the service via REST APIs

API call template:
```
http://localhost:8080/key2cluster/api/keyword?kws=keyword1,keyword2,keyword3...
```

## Build using Apache Maven
```
$ mvn package
```

## Build as a docker Docker container 
Once the application is packaged a docker container can be created automatically using maven with following command

```
$ mvn docker:build
```
and pushed

```
$ mvn docker:push
```
The user and container name can be configured in the *pom.xml* file.


## Run
```
$ java -jar target/key2cluster-1.0.0.jar -Dserver.port=8080 --spring.config.location=file:///${properties_file}
``` 

## Run as a Docker container
```
docker run --name keywords --rm  -v $(pwd)/files:/files -p 8080:8080 miciav/keywordtocluster:1.0.0 --spring.config.location=file:///${properties_file}

```

## Run with Docker Compose
A courtesy docker-compose.yml file is provided in src/main/docker. The application can be 
executed with the following command: 

```
$ docker-compose up -d 
```
Docker compose will take care of downloading the container from the Docker Hub and executing it against a mock custom event server.  


