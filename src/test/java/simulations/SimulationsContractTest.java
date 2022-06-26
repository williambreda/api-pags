
package simulations;

import base.Config;
import data.manager.SimulationDataManager;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.suite.SuiteTags.CONT;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class SimulationsContractTest extends Config {

    private static SimulationDataManager simulationDataFactory;

    @BeforeAll
    static void setup() {
        simulationDataFactory = new SimulationDataManager();
    }

    @Test
    @Tag(CONT)
    @DisplayName("Should validate the simulation schema for GET method")
    void getOneSimulation() {

        String existentCpf = simulationDataFactory.oneExistingSimulation().getCpf();
        given().
            pathParam("cpf", existentCpf).
        when().
            get("/simulacoes/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("json_schemas/simulations/simulations_schema.json"));
    }

    @Test
    @Tag(CONT)
    @DisplayName("Should validate the simulation schema for non-existing simulation")
    void simulationNotFound() {
        given().
            pathParam("cpf", simulationDataFactory.notExistentCpf()).
        when().
            get("/simulacoes/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("json_schemas/simulations/simulations_non_existent_smulation.json"));
    }

    @Test
    @Tag(CONT)
    @DisplayName("Should validate the simulation schema for missing information")
    void simulationWithMissingInformation() {

        given().
            contentType(ContentType.JSON).
            body(simulationDataFactory.missingAllData()).
        when().
            post("/simulacoes").
        then().
            body(matchesJsonSchemaInClasspath("json_schemas/simulations/simulations_missing_informations.json"));
    }
}
