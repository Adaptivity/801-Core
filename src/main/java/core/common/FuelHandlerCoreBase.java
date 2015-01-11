package core.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.IFuelHandler;

/**
 * @author Master801
 */
public final class FuelHandlerCoreBase implements IFuelHandler {

	private static final Map<ItemStack, Integer> burnTimeMapping = new HashMap<ItemStack, Integer>();
    public static final IFuelHandler INSTANCE = new FuelHandlerCoreBase();

	private FuelHandlerCoreBase() {
	}

	/**
	 * Make sure to add the object before the initialization event.
	 * @param fuel Can either be, Item, Block, or ItemStack.
	 */
	public static void addObjectToBeFuel(Object fuel, int burnTimePrimitive) {
        Integer burnTime = new Integer(burnTimePrimitive);
		if (fuel == null) {
			throw new CoreNullPointerException("The Object (fuel) is null!");
		}
		if (burnTime.intValue() < 1) {
			throw new CoreNullPointerException("The Fuel's burn time is null. Fuel: '%s', Burn Time: '%d'", fuel.toString(), burnTime);
		}
		if (fuel instanceof Item) {
			burnTimeMapping.put(new ItemStack((Item)fuel, 1, 0), burnTime.intValue());
		} else if (fuel instanceof Block) {
			burnTimeMapping.put(new ItemStack((Block)fuel, 1, 0), burnTime.intValue());
		} else if (fuel instanceof ItemStack) {
			burnTimeMapping.put(((ItemStack)fuel), burnTime.intValue());
		} else {
			throw new CoreNullPointerException("The Object is not an instance of a Block, an Item, or an ItemStack! Fuel: " + fuel);
		}
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		Integer burnTime = burnTimeMapping.get(fuel);
        if (burnTime != null) {
            return burnTime.intValue();
        }
        return 0;
	}

}
