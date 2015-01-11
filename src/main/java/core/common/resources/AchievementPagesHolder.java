package core.common.resources;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import core.helpers.AchievementHelper;
import core.helpers.AchievementHelper.NewAchievement;

/**
 * This Class holds all of Master801's (SonicJumper's originally), achievements.
 * @author Master801
 */
public final class AchievementPagesHolder {

	public static final class CraftingAndBuildingAchievementsPage {

		public static final Achievement buildShovel = AchievementHelper.createNewAchievement("buildShovel", 5, -3, Items.wooden_shovel, AchievementList.buildWorkBench);
		public static final Achievement buildIronPickaxe = AchievementHelper.createNewAchievement("buildSteelPickaxe", 9, 2, Items.iron_pickaxe, AchievementList.buildBetterPickaxe);
		public static final Achievement buildGoldPickaxe = AchievementHelper.createNewAchievement("buildGoldPickaxe", 10, 4, Items.golden_pickaxe, buildIronPickaxe).setSpecial();
		public static final Achievement buildDiamondPickaxe = AchievementHelper.createNewAchievement("buildDiamondPickaxe", 11, 2, Items.diamond_pickaxe, buildIronPickaxe).setSpecial();
		public static final Achievement buildAxe = AchievementHelper.createNewAchievement("buildAxe", -1, -1, Items.wooden_axe, AchievementList.buildWorkBench);
		public static final Achievement buildChest = AchievementHelper.createNewAchievement("buildChest", -1, -5, Blocks.chest, buildAxe);
		public static final Achievement mineCoal = AchievementHelper.createNewAchievement("mineCoal", 6, 4, Items.coal, AchievementList.buildPickaxe);
		public static final Achievement mineRedstone = AchievementHelper.createNewAchievement("mineRedstone", 9, 6, Items.redstone, buildIronPickaxe);
		public static final Achievement buildCompass = AchievementHelper.createNewAchievement("buildCompass", 11, 6, Items.compass, mineRedstone);
		public static final Achievement smeltCobbleStone = AchievementHelper.createNewAchievement("smeltCobbleStone", 5, 6, Blocks.stone, AchievementList.buildFurnace);
		public static final Achievement buildFlintAndSteel = AchievementHelper.createNewAchievement("buildFlintAndSteel", 3, 7, Items.flint_and_steel, AchievementList.acquireIron).setSpecial();
		public static final Achievement buildShears = AchievementHelper.createNewAchievement("buildShears", 1, 2, Items.shears, AchievementList.acquireIron);
		public static final Achievement collectWool = AchievementHelper.createNewAchievement("collectWool", -1, 3, Blocks.wool, buildShears);
		public static final Achievement buildBed = AchievementHelper.createNewAchievement("buildBed", -1, 1, Items.bed, collectWool);
		public static final Achievement enterCave = AchievementHelper.createNewAchievement("enterCave", 11, 1, Blocks.bedrock, AchievementList.killEnemy);
		public static final Achievement killSpider = AchievementHelper.createNewAchievement("killSpider", 14, 1, Items.string, enterCave);
		public static final Achievement killSlime = AchievementHelper.createNewAchievement("killSlime", 16, -1, Items.slime_ball, killSpider).setSpecial();
		public static final Achievement buildBow = AchievementHelper.createNewAchievement("buildBow", 14, -3, Items.bow, killSpider);
		public static final Achievement killCreeper = AchievementHelper.createNewAchievement("killCreeper", 16, -3, Items.gunpowder, buildBow);
		public static final Achievement getSkeletonToKillCreeper = AchievementHelper.createNewAchievement("getSkeletonToKillCreeper", 18, -3, Items.record_13, killCreeper).setSpecial();
		public static final Achievement buildJukebox = AchievementHelper.createNewAchievement("buildJukebox", 9, 14, Blocks.jukebox, mineRedstone).setSpecial();
		public static final Achievement buildTNT = AchievementHelper.createNewAchievement("buildTNT", 17, -2, Blocks.tnt, killCreeper).setSpecial();
		public static final Achievement killEnderman = AchievementHelper.createNewAchievement("killEnderman", 11, 0, Items.ender_pearl, AchievementList.killEnemy).setSpecial();
		public static final Achievement killZombie = AchievementHelper.createNewAchievement("killZombie", 14, -5, Items.rotten_flesh, buildBow);
		public static final Achievement killSkeleton = AchievementHelper.createNewAchievement("killSkeleton", 16, 1, Items.arrow, killSpider);
		public static final Achievement killPassiveMobForMeat = AchievementHelper.createNewAchievement("killPassiveMobForMeat", 11, -2, Items.porkchop, AchievementList.buildSword);
		public static final Achievement collectEgg = AchievementHelper.createNewAchievement("collectEgg", 11, -4, Items.egg, killPassiveMobForMeat);
		public static final Achievement collectRedApple = AchievementHelper.createNewAchievement("collectRedApple", -5, -4, Items.apple, buildAxe);
		public static final Achievement buildGoldenApple = AchievementHelper.createNewAchievement("buildGoldenApple", -6, -6, Items.gold_nugget, collectRedApple).setSpecial();
		public static final Achievement mineDirt = AchievementHelper.createNewAchievement("mineDirt", -5, 0, Blocks.dirt, AchievementList.openInventory);
		public static final Achievement buildClock = AchievementHelper.createNewAchievement("buildClock", 11, 8, Items.clock, mineRedstone);
		public static final Achievement buildRedstoneTorch = AchievementHelper.createNewAchievement("buildRedstoneTorch", 11, 10, Blocks.redstone_torch, mineRedstone);
		public static final Achievement buildRedstoneRepeater = AchievementHelper.createNewAchievement("buildRedstoneRepeater", 13, 10, Items.repeater, buildRedstoneTorch);
		public static final Achievement buildPiston = AchievementHelper.createNewAchievement("buildPiston", 11, 12, Blocks.piston, mineRedstone);
		public static final Achievement buildStickyPiston = AchievementHelper.createNewAchievement("buildStickyPiston", 13, 12, Blocks.sticky_piston, buildPiston);
		public static final Achievement mineSoulSand = AchievementHelper.createNewAchievement("mineSoulSand", -3, 10, Blocks.soul_sand, AchievementList.portal);
		public static final Achievement mineNetherrack = AchievementHelper.createNewAchievement("mineNetherrack", -1, 9, Blocks.netherrack, AchievementList.portal);
		public static final Achievement buildBrewingStand = AchievementHelper.createNewAchievement("buildBrewingStand", 1, 12, Blocks.brewing_stand, AchievementList.blazeRod);
		public static final Achievement mineSugarCane = AchievementHelper.createNewAchievement("mineSugarCane", 3, -7, Items.reeds, AchievementList.buildHoe);
		public static final Achievement buildPaper = AchievementHelper.createNewAchievement("buildPaper", 1, -7, Items.paper, mineSugarCane);
		public static final Achievement buildMap = AchievementHelper.createNewAchievement("buildMap", -1, -7, Items.map, buildPaper).setSpecial();
		public static final Achievement buildCookie = AchievementHelper.createNewAchievement("buildCookie", -4, -6, Items.cookie, collectRedApple).setSpecial();
		public static final Achievement buildNoteblock = AchievementHelper.createNewAchievement("buildNoteBlock", 7, 10, Blocks.noteblock, mineRedstone);
		public static final Achievement buildDispenser = AchievementHelper.createNewAchievement("buildDispenser", 7, 12, Blocks.dispenser, mineRedstone);
		public static final Achievement mineNetherWart = AchievementHelper.createNewAchievement("mineNetherWart", -3, 12, Items.nether_wart, mineSoulSand);
		public static final Achievement mineDragonEgg = AchievementHelper.createNewAchievement("mineDragonEgg", 5, 10, Blocks.dragon_egg, AchievementList.theEnd2).setSpecial();
		public static final Achievement mineCactus = AchievementHelper.createNewAchievement("mineCactus", -5, 2, Blocks.cactus, mineDirt);
		public static final Achievement mineCobblestone = AchievementHelper.createNewAchievement("mineCobblestone", -5, -2, Blocks.cobblestone, mineDirt);
		public static final Achievement mineWheat = AchievementHelper.createNewAchievement("mineWheat", 1, -5, Items.wheat, AchievementList.buildHoe);
		public static final Achievement liveFalling = AchievementHelper.createNewAchievement("liveFalling", 11, -1, Items.feather, null);
		public static final Achievement eatBacon = AchievementHelper.createNewAchievement("eatBacon", 12, -4, Items.porkchop, killPassiveMobForMeat);
		public static final Achievement level1 = AchievementHelper.createNewAchievement("level1", 19, 3, Items.wooden_sword, null);
		public static final Achievement level2 = AchievementHelper.createNewAchievement("level2", 21, 3, Items.wooden_sword, level1);
		public static final Achievement level3 = AchievementHelper.createNewAchievement("level3", 22, 4, Items.wooden_sword, level2);
		public static final Achievement level4 = AchievementHelper.createNewAchievement("level4", 20, 4, Items.wooden_sword, level3);
		public static final Achievement level5 = AchievementHelper.createNewAchievement("level5", 18, 4, Items.wooden_sword, level4);
		public static final Achievement level10 = AchievementHelper.createNewAchievement("level10", 17, 5, Items.wooden_sword, level5);
		public static final Achievement obsidian1 = AchievementHelper.createNewAchievement("obsidian1", 16, 12, Blocks.obsidian, null);
		public static final Achievement obsidian2 = AchievementHelper.createNewAchievement("obsidian2", 17, 12, Blocks.obsidian, obsidian1);
		public static final Achievement obsidian3 = AchievementHelper.createNewAchievement("obsidian3", 18, 11, Blocks.obsidian, obsidian2);
		public static final Achievement obsidian4 = AchievementHelper.createNewAchievement("obsidian4", 18, 10, Blocks.obsidian, obsidian2);
		public static final Achievement obsidian5 = AchievementHelper.createNewAchievement("obsidian5", 18, 9, Blocks.obsidian, obsidian2);
		public static final Achievement obsidian6 = AchievementHelper.createNewAchievement("obsidian6", 15, 11, Blocks.obsidian, obsidian5);
		public static final Achievement obsidian7 = AchievementHelper.createNewAchievement("obsidian7", 15, 10, Blocks.obsidian, obsidian5);
		public static final Achievement obsidian8 = AchievementHelper.createNewAchievement("obsidian8", 15, 9, Blocks.obsidian, obsidian5);
		public static final Achievement obsidian9 = AchievementHelper.createNewAchievement("obsidian9", 16, 8, Blocks.obsidian, obsidian8);
		public static final Achievement obsidian10 = AchievementHelper.createNewAchievement("obsidian10", 17, 8, Blocks.obsidian, obsidian8);
		public static final Achievement portal1 = AchievementHelper.createNewAchievement("portal1", 16, 11, Blocks.portal, obsidian10);
		public static final Achievement portal2 = AchievementHelper.createNewAchievement("portal2", 16, 10, Blocks.portal, obsidian10);
		public static final Achievement portal3 = AchievementHelper.createNewAchievement("portal3", 16, 9, Blocks.portal, obsidian10);
		public static final Achievement portal4 = AchievementHelper.createNewAchievement("portal4", 17, 11, Blocks.portal, obsidian10);
		public static final Achievement portal5 = AchievementHelper.createNewAchievement("portal5", 17, 10, Blocks.portal, obsidian10);
		public static final Achievement portal6 = AchievementHelper.createNewAchievement("portal6", 17, 9, Blocks.portal, obsidian10);

	}

	public static class DeathAchievementsPage {

		public static final Achievement killedByAny = new NewAchievement("killedByAny", 0, 0, Items.bone, null);
		public static final Achievement killedByCreeper = new NewAchievement("killedByCreeper", -2, 0, Blocks.tnt, killedByAny);
		public static final Achievement killedByEnderman = new NewAchievement("killedByEnderman", 2, 0, Blocks.end_stone, killedByAny);
		public static final Achievement killedByAnvil = new NewAchievement("killedByAnvil", 0, -1, Blocks.anvil, killedByAny);
		public static final Achievement killedByWall = new NewAchievement("killedByWall", 1, -1, Blocks.gravel, killedByAny);
		public static final Achievement killedByFlame = new NewAchievement("killedByFlame", -1, -1, Items.fire_charge, killedByAny);
		public static final Achievement killedByFire = new NewAchievement("killedByFire", 2, -1, Blocks.fire, killedByAny);
		public static final Achievement killedByBlaze = new NewAchievement("killedByBlaze", -2, -1, Items.blaze_rod, killedByAny);
		public static final Achievement killedByGhast = new NewAchievement("killedByGhast", 0, -2, Items.ghast_tear, killedByAny);
		public static final Achievement killedByFall = new NewAchievement("killedByFall", 1, -2, Items.brick, killedByAny);
		public static final Achievement killedByFireDispenser = new NewAchievement("killedByFireDispenser", -1, -2, Blocks.dispenser, killedByAny);
		public static final Achievement killedByArrowDispenser = new NewAchievement("killedByArrowDispenser", 2, -2, Blocks.dispenser, killedByAny);
		public static final Achievement killedByWater = new NewAchievement("killedByWater", -2, -2, Blocks.water, killedByAny);
		public static final Achievement killedByIron = new NewAchievement("killedByIron", 3, -2, Blocks.iron_block, killedByAny);
		public static final Achievement killedBySpace = new NewAchievement("killedBySpace", -3, -2, Blocks.bedrock, killedByAny);
		public static final Achievement killedByZombie = new NewAchievement("killedByZombie", 0, -3, Items.rotten_flesh, killedByAny);
		public static final Achievement killedByZombieVillager = new NewAchievement("killedByZombieVillager", 3, -3, Items.emerald, killedByAny);
		public static final Achievement killedByZombieVillagerChild = new NewAchievement("killedByZombieVillagerChild", -3, -3, Blocks.red_flower, killedByAny);
		public static final Achievement killedByZombieSword = new NewAchievement("killedByZombieSword", 0, -4, Items.iron_sword, killedByAny);
		public static final Achievement killedByZombieDiamond = new NewAchievement("killedByZombieDiamond", 1, -4, Items.diamond, killedByAny);
		public static final Achievement killedBySkeleton = new NewAchievement("killedBySkeleton", -1, -4, Items.arrow, killedByAny);
		public static final Achievement killedBySpider = new NewAchievement("killedBySpider", 2, -4, Items.spider_eye, killedByAny);
		public static final Achievement killedByDragon = new NewAchievement("killedByDragon", -2, -4, Blocks.end_portal_frame, killedByAny);
		public static final Achievement killedByWither = new NewAchievement("killedByWither", 3, -4, Blocks.skull, killedByAny);
		public static final Achievement killedByThrownPotion = new NewAchievement("killedByThrownPotion", -3, -4, Items.potionitem, killedByAny);
		public static final Achievement killedByPotion = new NewAchievement("killedByPotion", 0, -5, Items.potionitem, killedByAny);
		public static final Achievement killedBySmallSlime = new NewAchievement("killedBySmallSlime", 1, -5, Items.slime_ball, killedByAny);
		public static final Achievement killedByMediumSlime = new NewAchievement("killedByMediumSlime", -1, -5, Items.slime_ball, killedByAny);
		public static final Achievement killedByLargeSlime = new NewAchievement("killedByLargeSlime", 2, -5, Items.slime_ball, killedByAny);
		public static final Achievement killedByHugeSlime = new NewAchievement("killedByHugeSlime", -2, -5, Items.slime_ball, killedByAny);
		public static final Achievement killedBySelf = new NewAchievement("killedBySelf", 0, 1, Items.arrow, killedByAny);

		private DeathAchievementsPage() {
		}

		public static void registerAchievementDeathPage() {
			AchievementHelper.registerAchievementPage("Death", AchievementHelper.createNewAchievementArray(
					killedByAny, killedByCreeper, killedByEnderman, killedByEnderman, killedByAnvil, killedByWall,
					killedByFlame, killedByFire, killedByBlaze, killedByGhast, killedByFall, killedByFireDispenser,
					killedByFireDispenser, killedByArrowDispenser, killedByWater, killedByIron, killedBySpace,
					killedByZombie, killedByZombieVillager, killedByZombieVillagerChild, killedByZombieSword,
					killedByZombieDiamond, killedBySkeleton, killedBySpider, killedByDragon, killedByWither,
					killedByWither, killedByThrownPotion, killedByPotion, killedBySmallSlime, killedByMediumSlime,
					killedByLargeSlime, killedByHugeSlime, killedBySelf));
		}

	}

	public static class LevelAchievementsPage {

		public static final Achievement achieve1 = new NewAchievement("achieve1", 0, 0, Items.egg, null);
		public static final Achievement achieve2 = new NewAchievement("achieve2", 1, 0, Items.bow, achieve1);

		private LevelAchievementsPage() {
		}

		public static void registerAchievementLevelPage() {
			AchievementHelper.registerAchievementPage("Level", AchievementHelper.createNewAchievementArray(achieve1, achieve2));
		}

	}

	public static class NetherAchievementsPage {

		public static final Achievement achieve1 = new NewAchievement("achieve1", 0, 0, Items.egg, null);
		public static final Achievement achieve2 = new NewAchievement("achieve2", 1, 0, Items.bow, achieve1);

		private NetherAchievementsPage() {
		}

		public static void registerAchievementNetherPage() {
			AchievementHelper.registerAchievementPage("Nether", AchievementHelper.createNewAchievementArray(achieve1, achieve2));
		}

	}

	public static class TieredAchievementsPage {

		public static final Achievement mineSand = new NewAchievement("mineSand", -7, -7, Blocks.sand, null);
		public static final Achievement buildSandstone = new NewAchievement("buildSandstone", -8, -5, Blocks.sandstone, mineSand);
		public static final Achievement smeltSand = new NewAchievement("smeltGlass", -6, -5, Blocks.glass, mineSand);
		public static final Achievement buildGlassPane = new NewAchievement("buildGlassPane", -6, -3, Blocks.glass_pane, smeltSand);
		public static final Achievement mineSnow = new NewAchievement("mineSnow", -5, -7, Items.snowball, null);
		public static final Achievement buildSnow = new NewAchievement("buildSnow", -5, -5, Blocks.snow, mineSnow);
		public static final Achievement mineGlowStoneDust = new NewAchievement("mineGlowStoneDust", -3, -7, Items.glowstone_dust, null);
		public static final Achievement buildGlowStone = new NewAchievement("buildGlowStone", -3, -5, Blocks.glowstone, mineGlowStoneDust);
		public static final Achievement mineMushroomBrown = new NewAchievement("mineMushroomBrown", -2, -7, Blocks.brown_mushroom, null);
		public static final Achievement mineMushroomRed = new NewAchievement("mineMushroomRed", 0, -7, Blocks.red_mushroom, null);
		public static final Achievement makeMushroomStew = new NewAchievement("makeMushroomStew", -1, -5, Items.bowl, mineMushroomRed);
		public static final Achievement eatMushroomStew = new NewAchievement("eatMushroomStew", -1, -3, Items.bowl, makeMushroomStew);
		public static final Achievement mineClay = new NewAchievement("mineClay", 1, -7, Items.clay_ball, null);

		private TieredAchievementsPage() {
		}

		public static void registerAchievementTieredPages() {
			AchievementHelper.registerAchievementPage("Tiered", AchievementHelper.createNewAchievementArray(mineSand, buildSandstone, smeltSand, buildGlassPane, mineSnow, buildSnow, mineGlowStoneDust, buildGlowStone, mineMushroomBrown, mineMushroomRed, mineMushroomRed, makeMushroomStew, eatMushroomStew, mineClay));
		}

	}

}
