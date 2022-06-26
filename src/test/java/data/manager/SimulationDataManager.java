
package data.manager;


import com.github.javafaker.Faker;
import data.utils.CpfGenerator;
import models.Simulation;
import models.SimulationBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import java.math.BigDecimal;
import java.util.Random;
import static io.restassured.RestAssured.when;

public class SimulationDataManager {

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 40000;
    private static final int MIN_INSTALLMENTS = 2;
    private static final int MAX_INSTALLMENTS = 48;
    private final Faker faker;

    public SimulationDataManager() {
        faker = new Faker();
    }

    public String nonExistentName() {
        return faker.name().firstName();
    }

    public String notExistentCpf() {
        return new CpfGenerator().generate();
    }

    public Simulation validSimulation() {
        return newSimulation();
    }

    public Simulation oneExistingSimulation() {
        Simulation[] simulations = allSimulationsFromApi();
        return simulations[new Random().nextInt(simulations.length)];
    }

    public Simulation[] allExistingSimulations() {
        return allSimulationsFromApi();
    }

    public Simulation simulationInvalidMinValue() {
        Simulation simulation = validSimulation();
        simulation.setValor(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1)));

        return simulation;
    }

    public Simulation simulationInvalidMaxAmount() {
        Simulation simulation = validSimulation();
        simulation.setValor(new BigDecimal(faker.number().numberBetween(MAX_AMOUNT + 1, 99999)));

        return simulation;
    }

    public Simulation simulationLessThanMinInstallments() {
        Simulation simulation = validSimulation();
        simulation.setParcelas(MIN_INSTALLMENTS - 1);

        return simulation;
    }

    public Simulation simulationMoreThanMaxInstallments() {
        Simulation simulation = validSimulation();
        simulation.setParcelas(faker.number().numberBetween(MAX_INSTALLMENTS + 1, 999));

        return simulation;
    }

    public Simulation simulationWithNotValidEmail() {
        Simulation simulation = validSimulation();
        simulation.setEmail(faker.name().username());

        return simulation;
    }

    public Simulation simulationWithEmptyCPF() {
        Simulation simulation = validSimulation();
        simulation.setCpf(StringUtils.EMPTY);

        return simulation;
    }

    public Simulation simulationWithEmptyName() {
        Simulation simulation = validSimulation();
        simulation.setNome(StringUtils.EMPTY);

        return simulation;
    }

    public Simulation missingAllData() {
        return new SimulationBuilder().
            cpf(StringUtils.EMPTY).
            name(StringUtils.EMPTY).
            email(faker.name().username()).
            amount(new BigDecimal(faker.number().numberBetween(1, MIN_AMOUNT - 1))).
            installments(MIN_INSTALLMENTS - 1).
            insurance(faker.bool().bool()).
            build();
    }

    private Simulation[] allSimulationsFromApi() {
        return
            when().
                get("/simulacoes/").
            then().
                statusCode(HttpStatus.SC_OK).
                extract().
                    as(Simulation[].class);
    }

    private Simulation newSimulation() {
        return new SimulationBuilder().
            name(faker.name().nameWithMiddle()).
            cpf(new CpfGenerator().generate()).
            email(faker.internet().emailAddress()).
            amount(new BigDecimal(faker.number().numberBetween(MIN_AMOUNT, MAX_AMOUNT))).
            installments(faker.number().numberBetween(MIN_INSTALLMENTS, MAX_INSTALLMENTS)).
            insurance(faker.bool().bool()).build();
    }
}
