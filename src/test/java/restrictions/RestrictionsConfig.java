
package restrictions;

import base.Config;
import data.manager.RestrictionDataManager;
import org.junit.jupiter.api.BeforeAll;

public abstract class RestrictionsConfig extends Config {

    protected static RestrictionDataManager restrictionDataFactory;

    @BeforeAll
    static void setUpBase() {
        restrictionDataFactory = new RestrictionDataManager();
    }
}
