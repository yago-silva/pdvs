## Description

This is a pdv api that you can use to:

- Get the closer pdv that can attend you based on lng lat coordinates.
- Create a new pdv
- Get pdv by id  


## How to use

Pdv api provides two ways that you can interacts with:

- Rest api
- GraphQL

<b>Rest api</b>

Follow some examples of rest api calls:

Creating a pdv:
```
curl -X POST \
  http://localhost:8080/pdvs/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "tradingName": "Adega da Cerveja - Pinheiros",
    "ownerName": "Zé da Silva",
    "document": "96346527000141", 
    "coverageArea": { 
      "type": "MultiPolygon", 
      "coordinates": [
        [[[30, 20], [45, 40], [10, 40], [30, 20]]], 
        [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]
      ]
    },
    "address": { 
      "type": "Point",
      "coordinates": [-46.57421, -21.785741]
    }
}'
``` 

Get a pdv by id:

```
curl -X GET \
  http://localhost:8080/pdvs/5b3c57a6857aba00065584e3 \
  -H 'cache-control: no-cache'
```

Getting the closer pdv that can attend you based on lng lat coordinates:

```
curl -X GET \
  'http://localhost:8080/pdvs/closer?lng=25&lat=30' \
  -H 'cache-control: no-cache' 
```

Obs: You can explore swagger to more details. To access swagger starts the application on local env and access 
```
GET http://localhost:8080/swagger-ui.html
```  

<b>GraphQL</b>

Follow some examples of rest api calls:

Creating a pdv:

```
curl -X POST \
  http://localhost:8080/graphql/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
  "query": "mutation($pdv: PdvInputDto!) { save(input: $pdv){id} }",
  "variables": {
  	"pdv": {
	    "tradingName" : "Adega da Cerveja - Pinheiros",
	    "ownerName" : "Zé da Silva",
	    "document" : "96346527000141",
	    "coverageArea" : { 
	        "type": "MultiPolygon", 
	        "coordinates": [[[[30, 20], [45, 40], [10, 40], [30, 20]]], [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]]
	    	
	    },
	
	    "address": {
	      "type": "Point", 
	      "coordinates" : [-46.57421, -21.785741]
		}
	}
  }
}'
```

Get a pdv by id:

```
curl -X POST \
  http://localhost:8080/graphql/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
 "query": "{getPdvById(id:\"5b3c1a6a857aba00073273e6\"){id,tradingName,ownerName,document,address{type,coordinates},coverageArea{type,coordinates}}}"
}'
```

Getting the closer pdv that can attend you based on lng lat coordinates:

```
curl -X POST \
  http://localhost:8080/graphql/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
 "query": "{getCloserFrom(lng: 30, lat: 30){id,tradingName,ownerName,document,address{type,coordinates},coverageArea{type,coordinates}}}"
}'
```

## How to run on local environment

<b>Requirements:</b>
- Application will start exposing ports 8080 and 27017, so this ports should be free before application start.
- To run this application in your local environment you just need [docker](https://www.docker.com/) installed and running.


<b>Running:</b>

To start application just run the following command:

```
docker-compose down && ./gradlew clean build && docker-compose up --build
```

This command will start two docker containers, one running the pdv api and other running an mongodb 

Pdv api exposes the port 8080 that you can use to check status of application by calling the follow url
```
GET http://localhost:8080/health
``` 


## How to deploy in other environments

As application run inside a docker container, you can deploy it in any environment that supports docker.
You can create a new docker image running the following command:
```
./gradlew clean build && docker build .
``` 

<b>Important:</b> 

Remember that in local environment you used docker-compose to run application and so, a mongodb instance starts too.
In other environments you will need to configure pdv api docker container to use your mongodb instance by setting the following environment variables:

- MONGODB_HOST
- MONGODB_PORT
- MONGODB_AUTH_DATABASE
- MONGODB_USER_NAME
- MONGODB_USER_PASSWORD
- MONGODB_COLLECTION_NAME

## How to run tests 

To perform application tests, just run the following command:
```
./gradlew clean test
```

## Some used technologies:

- Java 8
- Spring Boot
- MongoDB
- GraphQL
- Docker