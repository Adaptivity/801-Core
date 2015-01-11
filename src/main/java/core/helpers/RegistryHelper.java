package core.helpers;

import core.Core;
import core.api.common.mod.IMod;
import core.api.integration.IModIntegrationHandler;
import core.api.network.proxy.IProxy;
import core.block.BlockCoreBase;
import core.client.renderer.block.BlockRendererCoreBase;
import core.common.GuiHandlerCoreBase;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreEnums.MiningLevelHelper;
import core.common.resources.CoreEnums.MiningToolHelper;
import core.item.ItemCoreBase;
import core.itemblock.ItemBlockCoreBase;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLStateEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RegistryHelper {

	private static final Map<IMod, GuiHandlerCoreBase> GUI_HANDLER_MAP = new HashMap<IMod, GuiHandlerCoreBase>();
	private static final List<Class<? extends TileEntity>> registeredTilesList = new ArrayList<Class<? extends TileEntity>>();
	private static final Map<IMod, List<IModIntegrationHandler>> MOD_INTEGRATION_HANDLER_MAP = new HashMap<IMod, List<IModIntegrationHandler>>();

	public static void registerBlock(BlockCoreBase block, String registeringName) {
		GameRegistry.registerBlock(block, "block|" + registeringName);
	}

	public static void registerBlock(BlockCoreBase block, Class<? extends ItemBlockCoreBase> itemblockClass, String registeringName) {
        String registryName = "block|" + registeringName;
        if (GameData.getBlockRegistry().containsKey(registryName)) {
            return;
        }
		GameRegistry.registerBlock(block, itemblockClass, registryName);
	}

	public static void registerItem(ItemCoreBase item, String registeringName) {
        String registryName = "item|" + registeringName;
        if (GameData.getItemRegistry().containsKey(registryName)) {
            return;
        }
		GameRegistry.registerItem(item, registryName);
	}

	public static void setBlockMiningLevel(BlockCoreBase block, MiningToolHelper tool, MiningLevelHelper level) {
		block.setHarvestLevel(tool.getTool(), level.getMiningLevel());
	}

	public static void setBlockMiningLevel(BlockCoreBase block, int metadata, MiningToolHelper tool, MiningLevelHelper level) {
		block.setHarvestLevel(tool.getTool(), level.getMiningLevel(), metadata);
	}

	public static void registerItemRenderer(Item item, IItemRenderer renderer) {
		MinecraftForgeClient.registerItemRenderer(item, renderer);
	}

	/**
	 * Registers the mod's gui handler.
	 * @param modInstance If the mod is not 'Core', then return your mod's instance ('Mod.instance'). Else if it is, enter null for the parameter.
	 */
	public static void registerGuiHandler(IMod modInstance) {
        if (modInstance == null) {
            return;
        }
        GuiHandlerCoreBase guiHandler = new GuiHandlerCoreBase();
        if (!GUI_HANDLER_MAP.containsKey(modInstance)) {
            CoreResources.getNetworkRegistry().registerGuiHandler(modInstance, guiHandler);
            GUI_HANDLER_MAP.put(modInstance, guiHandler);
        }
	}

	public static void registerTileEntity(Class<? extends TileEntity> tileClass, String registeringName) {
		if (tileClass == null) {
			throw new CoreNullPointerException("The TileEntity Class is null!");
		}
		if (registeringName == null) {
			throw new CoreNullPointerException("The registering name for the TileEntity Class is null!");
		}
        if (registeredTilesList.contains(tileClass)) {
            return;
        }
		registeredTilesList.add(tileClass);
		GameRegistry.registerTileEntity(tileClass, "tile|" + registeringName);
	}

    @SideOnly(Side.CLIENT)
	public static void registerBlockHandler(int renderID, BlockRendererCoreBase handler) {
		if (renderID <= 0) {
			throw new CoreNullPointerException("The Render ID is not valid! Block Renderer Name: '%s' Render ID: '%d'", handler.getRendererName(), renderID);
		}
		RenderingRegistry.registerBlockHandler(renderID, handler);
		LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully registered a block rendering handler! Renderer Name: '%s', RenderID: '%d'.", handler.getRendererName(), handler.getRenderId());
	}

    @SideOnly(Side.CLIENT)
    public static void registerBlockHandler(BlockRendererCoreBase handler) {
        IProxy proxy = ProxyHelper.getSidedProxyFromMod(handler.getOwningMod(), Side.CLIENT);
        if (proxy == null) {
            return;
        }
        if (proxy.getSide() == Side.SERVER) {
            return;
        }
        int renderID = ProxyHelper.getRenderIDFromSpecialID(proxy, handler.getSpecialRenderID());
        ProxyHelper.addRenderIDToMapping(proxy, handler.getSpecialRenderID(), renderID);
        registerBlockHandler(renderID, handler);
    }

    @SideOnly(Side.CLIENT)
	public static void bindSpecialRendererToTileEntity(Class<? extends TileEntity> tile, TileEntitySpecialRenderer specialRenderer) {
		RegistryHelper.bindSpecialRendererToTileEntity(tile, specialRenderer, false);
	}

    @SideOnly(Side.CLIENT)
	public static void bindSpecialRendererToTileEntity(Class<? extends TileEntity> tile, TileEntitySpecialRenderer specialRenderer, boolean hideMessage) {
		ClientRegistry.bindTileEntitySpecialRenderer(tile, specialRenderer);
		if (!hideMessage) {
			LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully binded the Special Renderer to TileEntity: '%s'!", tile.getSimpleName());
		}
	}

	public static GuiHandlerCoreBase getRegisteredGuiHandler(IMod instance) {
		if (instance == null) {
			throw new CoreNullPointerException("The IMod is null!");
		}
		if (!GUI_HANDLER_MAP.containsKey(instance)) {
			throw new CoreNullPointerException("There is no registered Gui Handler registered to the mod, '%s'! (Did you register it through RegistryHelper?)", instance.getModID());
		}
		return GUI_HANDLER_MAP.get(instance);
	}

    public static boolean isGuiHandlerRegistered(IMod mod) {
        if (mod == null) {
            return false;
        }
        return GUI_HANDLER_MAP.containsKey(mod) && GUI_HANDLER_MAP.get(mod) != null;
    }

	public static boolean isTileEntityRegistered(Class<? extends TileEntity> tile) {
		return registeredTilesList.contains(tile);
	}

    public static void setBlockAsFlammable(Block block, int encouragement, int flammability) {
        Blocks.fire.setFireInfo(block, encouragement, flammability);
    }

    public static void registerModIntegrationHandlers(IModIntegrationHandler... modIntegrationHandlers) {
        if (modIntegrationHandlers == null) {
            return;
        }
        if (modIntegrationHandlers.length <= 0) {
            return;
        }
        for(IModIntegrationHandler modIntegrationHandler : modIntegrationHandlers) {
            RegistryHelper.registerModIntegrationHandler(modIntegrationHandler);
        }
    }

	/**
	 * Make sure to add integrating mod as a dependency.
	 */
	public static void registerModIntegrationHandler(IModIntegrationHandler modIntegrationHandler) {
		if (modIntegrationHandler == null) {
			return;
		}
		List<IModIntegrationHandler> internalList = MOD_INTEGRATION_HANDLER_MAP.get(modIntegrationHandler.getParentMod());
		if (internalList == null) {
			internalList = new ArrayList<IModIntegrationHandler>();
		}
		if (!internalList.contains(modIntegrationHandler)) {
			internalList.add(modIntegrationHandler);
		}
        if (!Loader.isModLoaded(modIntegrationHandler.getModIDToIntegrateWith())) {
            return;
        }
		MOD_INTEGRATION_HANDLER_MAP.put(modIntegrationHandler.getParentMod(), internalList);
	}

	public static void postModEventToIntegrationHandlers(IMod mod, FMLStateEvent event) {
		if (mod == null) {
			return;
		}
		List<IModIntegrationHandler> internalList = MOD_INTEGRATION_HANDLER_MAP.get(mod);
		if (internalList == null) {
			MOD_INTEGRATION_HANDLER_MAP.put(mod, new ArrayList<IModIntegrationHandler>());
			return;
		}
		if (!internalList.isEmpty()) {
			for(IModIntegrationHandler modIntegrationHandler : internalList) {
				if (Loader.isModLoaded(modIntegrationHandler.getModIDToIntegrateWith())) {
                    if (event instanceof FMLPreInitializationEvent) {
                        modIntegrationHandler.addModBlocksAndItems();
                    }
					if (event instanceof FMLInitializationEvent) {
                        modIntegrationHandler.addModRecipes();
                    }
				}
			}
		}
	}

    static {
        RegistryHelper.registerGuiHandler(Core.instance);
    }

}
