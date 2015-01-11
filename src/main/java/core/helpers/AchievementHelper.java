package core.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

/**
 * SonicJumper is mainly the reason why this Class file even exists.
 * He was the one who introduced me to Achievements in the first place. :)
 * @author Master801
 */
public final class AchievementHelper {

	private AchievementHelper() {
	}

	public static void registerAchievementPage(String pageName, AchievementArray array) {
		AchievementPage.registerAchievementPage(new NewAchievementPage(pageName, array));
	}

	public static AchievementArray createNewAchievementArray(Achievement... achievements) {
		return new AchievementArray(achievements);
	}

	public static NewAchievement createNewAchievement(String achievementName, int displayColumn, int displayRow, Item item, Achievement parentAchievement) {
		return new NewAchievement(achievementName, displayColumn, displayRow, item, parentAchievement);
	}

	public static NewAchievement createNewAchievement(String achievementName, int displayColumn, int displayRow, Block block, Achievement parentAchievement) {
		return new NewAchievement(achievementName, displayColumn, displayRow, block, parentAchievement);
	}

	public static NewAchievement createNewAchievement(String achievementName, int displayColumn, int displayRow, ItemStack stack, Achievement parentAchievement) {
		return new NewAchievement(achievementName, displayColumn, displayRow, stack, parentAchievement);
	}

	public static final class AchievementArray {

		private final Achievement[] achievementArray;

		private AchievementArray(Achievement... array) {
			achievementArray = array;
		}

		public final Achievement[] getAchievementArray() {
			return achievementArray;
		}

	}

	private static final class NewAchievementPage extends AchievementPage implements IAchievement {

		private NewAchievementPage(String pageName, AchievementArray achievementArray) {
			super(pageName, achievementArray.getAchievementArray());
		}

	}

	public static final class NewAchievement extends Achievement implements IAchievement {

		public NewAchievement(String achievementName, int displayColumn, int displayRow, Item item, Achievement parentAchievement) {
			super(achievementName, achievementName, displayColumn, displayRow, item, parentAchievement);
			registerStat();
		}

		public NewAchievement(String achievementName, int displayColumn, int displayRow, Block block, Achievement parentAchievement) {
			super(achievementName, achievementName, displayColumn, displayRow, block, parentAchievement);
			registerStat();
		}

		public NewAchievement(String achievementName, int displayColumn, int displayRow, ItemStack stack, Achievement parentAchievement) {
			super(achievementName, achievementName, displayColumn, displayRow, stack, parentAchievement);
			registerStat();
		}

	}

	/**
	 * This interface is really only a dummy.
	 * @author Master801
	 */
	public static interface IAchievement {
	}

}
