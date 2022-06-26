
package simulations;

import base.Config;
import data.manager.SimulationDataManager;
import org.junit.jupiter.api.BeforeAll;


public abstract class SimulationsConfig extends Config {

    protected static SimulationDataManager simulationDataFactory;

    @BeforeAll
    static void setUpBase() {
        simulationDataFactory = new SimulationDataManager();
    }
}
