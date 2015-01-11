package core.item.food;

import net.minecraft.item.ItemFood;
import core.item.ItemCoreBase;

/**
 * @author Master801
 */
public abstract class ItemFoodCore extends ItemCoreBase {

	private final ItemFood food;

	public ItemFoodCore(int healAmount, float saturationModifier, boolean isWolfsFavoriteMeat, boolean doesHaveSubtypes) {
		super(doesHaveSubtypes);
		food = new ItemFood(healAmount, saturationModifier, isWolfsFavoriteMeat);
		food.setHasSubtypes(hasSubtypes);
	}

	public final ItemFood getFood() {
		return food;
	}

}
