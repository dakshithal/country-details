# Steps to run

## Using [Docker](https://www.docker.com/)
1. Once the repository is cloned `cd` to project's home folder
2. Excute command `docker compose up`
3. Wait for the application to start
4. The backend will start on port 8080 and swagger url is http://localhost:8080/swagger-ui/index.html
5. The ui will start on http://localhost:3000/

## Using maven, Java 8 and node
### Starting the backend using [maven](https://maven.apache.org/) and [Java 8](https://www.oracle.com/java/technologies/downloads/)
1. Once the repository is cloned `cd` to `assignment` folder inside project's home folder
2. Build the project by executing command `mvn clean package`
3. Start the application by executing command `java -jar target/assignment-0.0.1-SNAPSHOT.jar`. Alternatively can use command `mvn spring-boot:run`
4. The backend will start on port 8080 and swagger url is http://localhost:8080/swagger-ui/index.html

### Starting the ui using [node](https://nodejs.org/en/)
1. Once the repository is cloned `cd` to `ui` folder inside project's home folder
2. Install the dependancies by executing command `npm install`
3. Start the application by executing command `npm start`
4. The ui will start on http://localhost:3000/

# Steps to test

## Running automated tests using [maven](https://maven.apache.org/) and [Java 8](https://www.oracle.com/java/technologies/downloads/)
1. Once the repository is cloned `cd` to `assignment` folder inside project's home folder
2. Run the automated tests by executing command `mvn clean test`

## Test using swaggger
1. Start the application using docker or start only the backend usingmaven and java using (Refer [Steps to run](#steps-to-run)
2. Navigate to [swagger ui](http://localhost:8080/swagger-ui/index.html)
<img width="1461" alt="swagger ui" src="https://user-images.githubusercontent.com/20994493/194751938-b2e61b38-a1f5-44b1-b257-021f801b867c.png">

3. Expand the controllers by clicking on them
4. Click on `Try it out` to enble testing
<img width="1461" alt="swagger try it out" src="https://user-images.githubusercontent.com/20994493/194752166-5c900686-d603-4b12-865e-3f7923804681.png">

5. Click on `Execute` to test the api (Provide any parameters as necessary)
<img width="1449" alt="Screenshot 2022-10-09 at 13 49 16" src="https://user-images.githubusercontent.com/20994493/194752621-20a3c5da-e61a-4b0d-b97a-c0d17a001335.png">


## Test using ui
1. Start the application using [Steps to run](#steps-to-run)
2. [Open the ui](http://localhost:3000/)
3. You can select a country from the *List of Countries* on the left panel to see its details on the right panel
<kbd><img width="1565" alt="image" src="https://user-images.githubusercontent.com/20994493/194753543-799cc58b-0d6a-48d9-a2ba-52f64d6fc86c.png"/></kbd>

## Test using other api clients (eg: Postman(https://www.postman.com/))
Alternatively you can use any third party api client for testing the apis. The urls for testing will be:
- Country List: http://localhost:8080/v1/countries
- Country Details: http://localhost:8080/v1/countries/{name}
