
package simulations;


import io.restassured.http.ContentType;
import models.Simulation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static data.suite.SuiteTags.FUNC;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;



public class SimulationsFunctionalTest extends SimulationsConfig {

    @Test
    @Tag(FUNC)
    @DisplayName("Must validate one existing simulation")
    void returnOneExistingSimulation() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("cpf", existingSimulation.getCpf()).
        when().
            get("/simulacoes/{cpf}").
        then().
            statusCode(SC_OK).
            body(
                "nome", equalTo(existingSimulation.getNome()),
                "cpf", equalTo(existingSimulation.getCpf()),
                "email", equalTo(existingSimulation.getEmail()),
                "valor", equalTo(existingSimulation.getValor()),
                "parcelas", equalTo(existingSimulation.getParcelas()),
                "seguro", equalTo(existingSimulation.getSeguro())
            );
    }

    @Test
    @Tag(FUNC)
    @DisplayName("Must validate all existing simulations")
    void returnAllExistingSimulations() {
        Simulation[] existingSimulations = simulationDataFactory.allExistingSimulations();

        Simulation[] simulationsRequested =
            when().
                get("simulacoes/").
            then().
                statusCode(SC_OK).
                extract().
                   as(Simulation[].class);

        Assertions.assertThat(existingSimulations).contains(simulationsRequested);
    }

    @Test
    @Tag(FUNC)
    @DisplayName("Must filter by name a non-existing simulation")
    void returnSimulationByNameNotFound() {

        given().
            pathParam("nome", simulationDataFactory.nonExistentName()).
        when().
            get("/simulacoes/{nome}").
        then().
            statusCode(SC_NOT_FOUND);
    }



    @Test
    @Tag(FUNC)
    @DisplayName("Must create a new simulation.")
    void createNewSimulationSuccessfully() {
        Simulation simulation = simulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulacoes").
        then().
            statusCode(SC_CREATED);

    }


    @Test
    @Tag(FUNC)
    @DisplayName("Must try create a new simulation with invalid max value.")
    void createNewSimulationWithInvalidMaxValue() {
        Simulation simulation = simulationDataFactory.simulationInvalidMaxAmount();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulacoes").
        then().
            statusCode(SC_BAD_REQUEST);
    }

    @Test
    @Tag(FUNC)
    @DisplayName("Must try create a new simulation with invalid min number of installments.")
    void createNewSimulationWithInvalidMinInstallments() {
        Simulation simulation = simulationDataFactory.simulationLessThanMinInstallments();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulacoes").
        then().
            statusCode(SC_BAD_REQUEST);
    }



    @Test
    @Tag(FUNC)
    @DisplayName("Must try create a new simulation with invalid email.")
    void createNewSimulationWithInvalidEmail() {
        Simulation simulation = simulationDataFactory.simulationWithNotValidEmail();

        given().
            contentType(ContentType.JSON).
            body(simulation).
        when().
            post("/simulacoes").
        then().
            statusCode(SC_BAD_REQUEST);
    }





    @Test
    @Tag(FUNC)
    @DisplayName("Must delete an existing simulation and validate exclusion")
    void deleteSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        given().
            pathParam("id", existingSimulation.getId()).
        when().
            delete("/simulacoes/{id}").
        then().
            statusCode(SC_OK);
        //Back-end returning 200 on simulation already deleted, in the documentation it was 204;
        given().
                pathParam("cpf", existingSimulation.getCpf()).
        when().
                get("/simulacoes/{cpf}").
        then().
                statusCode(SC_NOT_FOUND);

    }


    @Test
    @Tag(FUNC)
    @DisplayName("Must update an existing simulation")
    void changeSimulationSuccessfully() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();

        Simulation simulation = simulationDataFactory.validSimulation();
        simulation.setCpf(existingSimulation.getCpf());
        simulation.setSeguro(existingSimulation.getSeguro());

        Simulation simulationReturned =
            given().
                contentType(ContentType.JSON).
                pathParam("cpf", existingSimulation.getCpf()).
                body(simulation).
            when().
                put("/simulacoes/{cpf}").
            then().
                statusCode(SC_OK).
                extract().
                    as(Simulation.class);

        Assert.assertNotEquals(simulationReturned, simulation);
    }

    @Test
    @Tag(FUNC)
    @DisplayName("Must update an existing simulation with all invalid data")
    void changeSimulationInvalidData() {
        Simulation existingSimulation = simulationDataFactory.oneExistingSimulation();
        Simulation invalidSimulation = simulationDataFactory.missingAllData();

        given().
            contentType(ContentType.JSON).
            pathParam("cpf", existingSimulation.getCpf()).
            body(invalidSimulation).
        when().
            put("/simulacoes/{cpf}").
        then().
            statusCode(SC_BAD_REQUEST);
    }


    @Test
    @Tag(FUNC)
    @DisplayName("Must validate the return of an update for a non-existent CPF")
    void changeSimulationCpfNotFound() {
        Simulation simulation = simulationDataFactory.validSimulation();

        given().
            contentType(ContentType.JSON).
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
            body(simulation).
        when().
            put("/simulacoes/{cpf}").
        then().
            statusCode(SC_NOT_FOUND);
    }
}
