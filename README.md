[![Build Status](https://travis-ci.org/aydarik/address-parser.svg?branch=master)](https://travis-ci.org/aydarik/address-parser)

# Addressline

This is a simple Java application with REST API that helps to parse a given address line to a suitable JSON format.
It provided with CI/CD workflow with testing, building and delivering it as a Docker container to the Docker hub.

## Profiles
The application can parse an address locally with it's own implemented logic, or by the help of the OpenStreetMap API named *Nominatim*.
* Local parsing is default, so you can just run it as is, or pass a **'local'** parameter to the enviroment variable:

```
export spring_profiles_active=local
```

* OSM parsing will be enabled if the profile set to **'osm'**:

```
export spring_profiles_active=osm
```

## Building

Generally it's build up automatically by *Travis CI* after each commit to the repo, but you are always welcome to build it yourself:

`mvn clean package` for building a jar-file;

`docker build -t aydarik/address-parser .` will package it to a Docker container.

### Running

The easiest way to run it, is to use Docker Compose command, because it should be already delivered to the [Docker hub](https://hub.docker.com/r/aydarik/address-parser):

Use `docker-compose up -d` to run, and `docker-compose down` to stop.

Or you can also use Docker command to run it because no other containers needed:

```
docker run --rm -e spring_profiles_active=${spring_profiles_active} -p 8080:8080 aydarik/address-parser
```

And of course `java -jar ./target/address-parser.jar` after you build it to run the application without any containers.
It already contains *Apache Tomcat* inside, so you don't need to deploy it.

### Usage

Once you start the application, it should be available on 8080 port.
It contains *Swagger UI*, so you can see API docs on http://localhost:8080/swagger-ui.html as well as to use it for manual testing purposes.
Address Parser API itself is available by making a GET request to `http://localhost:8080/api/v1` with passed 'address' parameter.

Examples:

```
$ curl -G -s 'http://localhost:8080/api/v1' --data-urlencode 'address=Winterallee 3' | jq .
{
  "street": "Winterallee",
  "housenumber": "3"
}
```

```
$ curl -G -s 'http://localhost:8080/api/v1' --data-urlencode 'address=4, rue de la revolution' | jq .
{
  "street": "rue de la revolution",
  "housenumber": "4"
}
```