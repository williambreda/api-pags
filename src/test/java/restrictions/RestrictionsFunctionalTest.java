
package restrictions;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;


import static data.suite.SuiteTags.FUNC;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

class RestrictionsFunctionalTest extends RestrictionsConfig {

    @Test
    @Tag(FUNC)
    @DisplayName("Must get a CPF without restriction")
    void cpfWithNoRestriction() {
        given().
            pathParam("cpf", restrictionDataFactory.cpfWithoutRestriction()).
        when().
            get("/restricoes/{cpf}").
        then()
            .statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Test
    @Tag(FUNC)
    @DisplayName("Must get a CPF with restriction")
    void cpfWithRestriction() {
        String cpfWithRestriction = restrictionDataFactory.cpfWithRestriction();

        given().
            pathParam("cpf", cpfWithRestriction).
        when().
            get("/restricoes/{cpf}").
        then()
            .statusCode(HttpStatus.SC_OK).
            body("mensagem",
                is(MessageFormat.format("O CPF {0} tem problema", cpfWithRestriction)));
    }

    @Test
    @Tag(FUNC)
    @DisplayName("Must get a CPF incomplete")
    void cpfIncomplete() {
        String cpfIncomplete = restrictionDataFactory.cpfIncomplete();

        given().
            pathParam("cpf", cpfIncomplete).
        when().
            get("/restricoes/{cpf}").
        then()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
