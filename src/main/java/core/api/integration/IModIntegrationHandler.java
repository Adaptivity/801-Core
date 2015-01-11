package core.api.integration;

import core.api.common.mod.IMod;

/**
 * Created by Master801 on 11/27/2014 at 8:20 PM.
 * @author Master801
 */
public interface IModIntegrationHandler {

    void addModBlocksAndItems();

    void addModRecipes();

    IMod getParentMod();

    /**
     * Note, this <b>is</b> case-sensitive.
     * @return The mod's id to integrate this class with.
     */
    String getModIDToIntegrateWith();

}
