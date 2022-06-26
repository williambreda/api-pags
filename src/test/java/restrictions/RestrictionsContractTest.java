
package restrictions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static data.suite.SuiteTags.CONT;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class RestrictionsContractTest extends RestrictionsConfig {

    @Test
    @Tag(CONT)
    @DisplayName("Must validate the restrictions schema for GET method with a valid cpf")
    void contractOnValidCPF() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithRestriction()).
        when().
            get("/restricoes/{cpf}").
        then().
            body(matchesJsonSchemaInClasspath("json_schemas/restrictions/restrictions_schema.json"));

    }
}
