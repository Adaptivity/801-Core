package core.api.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

/**
 * Created by Master801 on 11/11/2014.
 * @author Master801
 */
public interface IGuiUpdateData {

    void readFromGuiData(int id, int value);

    void writeToGuiData(Container container, ICrafting crafting);

}
