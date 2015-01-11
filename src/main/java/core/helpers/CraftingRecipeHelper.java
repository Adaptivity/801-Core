package core.helpers;

import java.util.List;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import core.Core;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;

/**
 * @author Master801
 */
public final class CraftingRecipeHelper {

	/**
	 * Note, not furnace recipes, or any other kind of recipes like that.
	 * It's purely just, recipes, shapeless-recipes, and shaped-recipes.
	 */
	public static IRecipe[] getAllRegisteredRecipes() {
		final List<IRecipe> recipeList = CoreResources.getCraftingManager().getRecipeList();
		final IRecipe[] allRecipes = new IRecipe[recipeList.size()];
		for(int i = 0; i < recipeList.size(); i++) {
			allRecipes[i] = recipeList.get(i);
		}
		return allRecipes;
	}

	public static void removeShapedCraftingRecipe(ShapedRecipes recipe) {
		if (recipe == null) {
			throw new CoreNullPointerException("The ShapedRecipes is null!");
		}
		for(int i = 0; i < CoreResources.getCraftingManager().getRecipeList().size(); i++) {
			ShapedRecipes removingRecipe = (ShapedRecipes)CoreResources.getCraftingManager().getRecipeList().get(i);
			if (removingRecipe.equals(recipe)) {
				CoreResources.getCraftingManager().getRecipeList().remove(i);
				LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully removed the Shaped Recipe '%s'!", removingRecipe.getRecipeOutput().getUnlocalizedName());
			}
		}
	}

	public static void removeShapelessCraftingRecipe(ShapelessRecipes recipe) {
		if (recipe == null) {
			throw new CoreNullPointerException("The ShapelessRecipes is null!");
		}
		for(int i = 0; i < CoreResources.getCraftingManager().getRecipeList().size(); i++) {
			ShapelessRecipes removingRecipe = (ShapelessRecipes)CoreResources.getCraftingManager().getRecipeList().get(i);
			if (removingRecipe.equals(recipe)) {
				CoreResources.getCraftingManager().getRecipeList().remove(i);
				LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully removed the Shapeless Recipe '%s'!", removingRecipe.getRecipeOutput().getUnlocalizedName());
			}
		}
	}

}
