package core.item.tools.pickaxe;

import net.minecraft.item.ItemPickaxe;
import core.helpers.ItemHelper;
import core.item.tools.ItemToolCore;

public abstract class ItemCorePickaxeBase extends ItemToolCore {

	private final ItemPickaxe pickaxe;

	public ItemCorePickaxeBase(boolean doesHaveSubtypes, ToolMaterial material) {
		super(doesHaveSubtypes, 2.0F, material, ItemHelper.getPickaxeBlocksEffectiveAgainstField());
		pickaxe = new ItemPickaxe(material) {
		};
		pickaxe.setHasSubtypes(hasSubtypes);
	}

	public final ItemPickaxe getPickaxe() {
		return pickaxe;
	}

}