# API Pags - Technical Challenge

This is the API project used for the technical challenge, it is published on the Azure cloud:

https://api-pags.azurewebsites.net/swagger-ui.html#

##  Requirements to run local
 * Java JDK 11 must be installed
 * Maven must be installed and configured in the application path
 
## How to run the application

At the project root, through your Commando/Terminal/Console Prompt run the command 

```bash
mvn clean spring-boot:run
```


The application will be available via the URL [http://localhost:8080](http://localhost:8080)

You can change the application port, if _8080_ is already in use, by adding the JVM property `server.port`.

Example:

```bash
mvn clean spring-boot:run -Dserver.port=8888
```
## How to run the test suite:

Run only tests with the designated tag:

```sh
mvn test -Dgroups="functional"
```
```sh
mvn test -Dgroups="contract"
```
Or to run the complete suite, regardless of the associated tag:

```sh
mvn test
```

## Test execution reports
The test execution report is stored on github for each job in which it runs:


https://williambreda.github.io/api-pags

## Application technical documentation

API technical documentation is available through OpenAPI/Swagger at
 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) ou
[https://api-pags.azurewebsites.net/swagger-ui.html#/](https://api-pags.azurewebsites.net/swagger-ui.html#/)

## Rules

### Restrictions

`GET <host>/api/v1/restricoes/{cpf}`

The Restrictions endpoint is intended to query the CPF informing, returning whether or not it has a restriction.


* If there is no restriction, HTTP Status 204 is returned
* If there is restriction, HTTP Status 200 is returned with the message "The CPF 99999999999 has restriction"


#### CPFs with restriction

| CPF |
| ----|
| 97093236014 |
| 60094146012 |
| 84809766080 |
| 62648716050 |
| 26276298085 |
| 01317496094 |
| 55856777050 |
| 19626829001 |
| 24094592008 |
| 58063164083 |

### Simulations

The simulation is a register that will record important information about the credit such as value, installments,
contact details, etc.

### Create a simulation

`POST <host>/api/v1/simulacoes`

This endpoint is responsible for inserting a new simulation.

There are the following attributes to be informed, with their respective rules:


* A successfully registered simulation returns HTTP Status 201 and the data entered as a return
* A simulation with a problem with some rule returns HTTP Status 400 with the list of errors.
* A simulation for the same CPF returns an HTTP Status 409 with the message "CPF already exists".


### Update a simulation

`PUT <host>/api/v1/simulacoes/{cpf}`

Changes an existing simulation, where the CPF must be informed so that the change can be made.


* The change can be made to any attribute of the simulation
* Same rules remain
* If the CPF does not have a simulation, the HTTP Status 404 is returned with the message "CPF not found"


### Consult all registered simulations

`GET <host>/api/v1/simulacoes`

Lists the registered simulations.

* Returns the list of registered simulations and there is one or more
* Returns HTTP Status 204 if there are no simulations registered

### Consult a simulation by CPF


`GET <host>/api/v1/simulacoes/{cpf}`

Returns the simulation previously registered by the CPF.

* Returns the registered simulation
* If the CPF does not have a simulation, HTTP Status 404 is returned

### Remove a simulation

`DELETE <host>/api/v1/simulacoes/{id}`

Removes a simulation previously registered by its ID.

* Returns HTTP Status 204 if simulation is successfully removed
* Returns HTTP Status 404 with the message "Simulation not found" if there is no simulation by the given ID
