package core.helpers;

import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindFieldException;
import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is based off of {@link cpw.mods.fml.common.ObfuscationReflectionHelper}.
 * @author Master801
 */
public final class MinecraftObfuscationHelper {

    /**
     * This contains both methods and fields.
     */
    private static final Map<String, String> SRG_TO_MCP_MAPPING = new HashMap<String, String>();
    private static final Map<String, String> OBFUSCATED_CLASS_TO_DEOBFUSCATED_CLASS = new HashMap<String, String>();

	public static String[] remapMethodNames(String className, String... methodNames) {
		String internalClassName = MinecraftObfuscationHelper.remapClass(false, className);
		String[] mappedNames = new String[methodNames.length];
		int i = 0;
		for (String mName : methodNames) {
			mappedNames[i] = CoreResources.getRemapper().mapMethodName(internalClassName, mName, null);
		}
		return mappedNames;
	}

	private static Method findMethod(Class<?> clazz, ClassArray classArray, String... methodNames) {
		Exception failed = null;
		for (String methodName : methodNames) {
			try {
				Method m;
				if (classArray.getArray() == new Class[0]) {
					m = clazz.getDeclaredMethod(methodName);
				} else {
					m = clazz.getDeclaredMethod(methodName, classArray.getArray());
				}
				m.setAccessible(true);
				return m;
			}
			catch (Exception e) {
				failed = e;
			}
		}
		throw new UnableToFindFieldException(methodNames, failed);
	}

	private static <T, E> T getPrivateValueAndFindMethod(Class <? super E > classToAccess, E instance, ClassArray classArray, ObjectArray objectArray, String... fieldNames) {
		try {
			if (objectArray.getObjects() == new Object[0]) {
				return (T) findMethod(classToAccess, classArray, fieldNames).invoke(instance);
			} else {
				return (T) findMethod(classToAccess, classArray, fieldNames).invoke(instance, objectArray.getObjects());
			}
		} catch (Exception e) {
			throw new UnableToAccessMethodException(fieldNames, e);
		}
	}

	static <T, E> T getPrivateValue(Class<? super E> clazz, E instance, ClassArray classArray, ObjectArray objectArray, String... names) {
		try {
			return getPrivateValueAndFindMethod(clazz, instance, classArray, objectArray, MinecraftObfuscationHelper.remapMethodNames(clazz.getName(), names));
		} catch (UnableToFindFieldException e) {
			FMLLog.log(Level.ERROR,e,"Unable to locate any method %s on type %s", Arrays.toString(names), clazz.getName());
			throw e;
		} catch (UnableToAccessMethodException e) {
			FMLLog.log(Level.ERROR, e, "Unable to access any method %s on type %s", Arrays.toString(names), clazz.getName());
			throw e;
		}
	}

	public static ClassArray createNewParameterArray(Class... parameters) {
		return new ClassArray(parameters);
	}

	public static ObjectArray createNewObjectArray(Object... objects) {
		return new ObjectArray(objects);
	}

	/**
	 * To make it less confusing.
	 * This is only used for getting field values with the instance of the Class.
	 * (Non-static fields.)
	 */
	public static <T, E> T getMinecraftFieldValue(Class<? super E> clazz, E instance, String srgName, String deobName) {
		return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, srgName, deobName);
	}

	/**
	 * To make it less confusing.
	 * This is only used for getting field values without the instance of the Class.
	 * (Static fields.)
	 */
	public static <T, E> T getMinecraftFieldValue(Class<? super E> clazz, String srgName, String deobName) {
		return ObfuscationReflectionHelper.getPrivateValue(clazz, null, srgName, deobName);
	}

	public static <E> void setMinecraftFieldValue(Class<? super E> clazz, E instance, String srgName, String deobName, Object value) {
		ObfuscationReflectionHelper.setPrivateValue(clazz, instance, value, srgName, deobName);
	}

	public static <E> void setMinecraftFieldValue(Class<? super E> clazz, String srgName, String deobName, Object value) {
		ObfuscationReflectionHelper.setPrivateValue(clazz, null, value, srgName, deobName);
	}

	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, E instance, String srgName, String deobName, ClassArray classArray, Object... parameters) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, instance, classArray, MinecraftObfuscationHelper.createNewObjectArray(parameters), srgName, deobName);
	}

	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, String srgName, String deobName, ClassArray classArray, Object... parameters) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, null, classArray, MinecraftObfuscationHelper.createNewObjectArray(parameters), srgName, deobName);
	}

	/**
	 * Only used for void methods.
	 */
	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, E instance, String srgName, String deobName, ClassArray classArray) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, instance, classArray, MinecraftObfuscationHelper.createNewObjectArray(), srgName, deobName);
	}

	/**
	 * Only used for static, and void methods.
	 */
	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, String srgName, String deobName, ClassArray classArray) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, null, classArray, MinecraftObfuscationHelper.createNewObjectArray(), srgName, deobName);
	}

	public static <E> void invokeMinecraftMethod(Class<? super E> clazz, E instance, String srgName, String deobName, ClassArray classArray, Object... values) {
		MinecraftObfuscationHelper.getPrivateValue(clazz, instance, classArray, MinecraftObfuscationHelper.createNewObjectArray(values), srgName, deobName);
	}

	public static <E> void invokeMinecraftMethod(Class<? super E> clazz, String srgName, String deobName, ClassArray classArray, Object... values) {
		MinecraftObfuscationHelper.getPrivateValue(clazz, null, classArray, MinecraftObfuscationHelper.createNewObjectArray(values), srgName, deobName);
	}

    public static String remapClass(boolean remapToObfuscated, String clazz) {
        if (remapToObfuscated) {
            Map<String, String> reversedMap = RandomHelper.reverseMapContents(MinecraftObfuscationHelper.OBFUSCATED_CLASS_TO_DEOBFUSCATED_CLASS);
            return reversedMap.get(clazz);
        }
        return MinecraftObfuscationHelper.OBFUSCATED_CLASS_TO_DEOBFUSCATED_CLASS.get(clazz);
    }

    public static String getMCPFromSRGF(String srg) {
        if (srg == null) {
            return null;
        }
        if (!srg.startsWith("field_")) {
            return srg;//In-case you made a mistake, and inputted a non-srg name.
        }
        String mcp = MinecraftObfuscationHelper.SRG_TO_MCP_MAPPING.get(srg);
        if (mcp == null) {
            throw new CoreNullPointerException("Failed to get a MCP name from the SRG! SRG: %s", srg);
        } else {
            srg = mcp;
        }
        return srg;
    }

    public static String getMCPFromSRGM(String srg) {
        if (srg == null) {
            return null;
        }
        if (!srg.startsWith("func_")) {
            return srg;//In-case you made a mistake, and inputted a non-srg name.
        }
        String mcp = MinecraftObfuscationHelper.SRG_TO_MCP_MAPPING.get(srg);
        if (mcp == null) {
            throw new CoreNullPointerException("Failed to get a MCP name from the SRG! SRG: %s", srg);
        } else {
            srg = mcp;
        }
        return srg;
    }

    public static void setupMappings(String mappingFile) throws IOException {
        InputStream stream = InputStreamHelper.getStreamFromResource(mappingFile, false);
        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        String lineToRead;
        while((lineToRead = reader.readLine()) != null) {
            String entireLineWithoutFront = lineToRead.substring(4);

            //Patches for methods.
            String removed0 = entireLineWithoutFront.replace(" ()Ljava/lang/String;", "").replace(" ()Lnet/minecraft/world/chunk/IChunkProvider;", "").replace(" (Lnet/minecraft/client/particle/EntityFX;)V", "").replace(" (Lnet/minecraft/tileentity/TileEntityEnchantmentTable;DDDF)V", "");
            String removed1 = removed0.replace(" (Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", "").replace(" (III)Z", "").replace(" (Lnet/minecraft/client/renderer/Tessellator;Lnet/minecraft/util/IIcon;)V", "").replace(" (IIILnet/minecraft/block/Block;)V", "");
            String removed2 = removed1.replace(" (Lnet/minecraft/client/entity/AbstractClientPlayer;)Lnet/minecraft/util/ResourceLocation;", "").replace(" ()Z", "").replace(" ()V", "").replace(" ()D", "").replace(" ()I", "").replace(" ()B", "").replace(" (Lnet/minecraft/world/World;IIII)V", "");
            String removed3 = removed2.replace(" (Lnet/minecraft/entity/player/EntityPlayer;)Z", "").replace(" ()Lnet/minecraft/item/ItemStack;", "").replace(" ()Lnet/minecraft/item/Item;", "").replace(" ()Ljava/util/List;", "").replace(" (Lnet/minecraft/item/ItemStack;)Ljava/lang/String;", "");
            String removed4 = removed3.replace(" (Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/EnumRarity;", "").replace(" (Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;Z)V", "").replace(" (Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;", "");
            String removed5 = removed4.replace(" (Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)Z", "").replace(" (Lnet/minecraft/entity/passive/EntityVillager;DDDFF)V", "").replace(" (Lnet/minecraft/world/World;III)V", "").replace(" (F)F", "").replace(" (FF)V", "");
            String removed6 = removed5.replace(" (Ljava/lang/String;)V", "").replace(" (Lnet/minecraft/client/renderer/texture/IIconRegister;)V", "").replace(" (Lnet/minecraft/util/EnumChatFormatting;)Lnet/minecraft/util/ChatStyle;", "").replace(" (Lnet/minecraft/item/Item;Lnet/minecraft/creativetab/CreativeTabs;Ljava/util/List;)V", "");
            String removed7 = removed6.replace(" (Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;", "").replace(" (II)Lnet/minecraft/util/IIcon;", "").replace(" (I)Lnet/minecraft/util/IIcon;", "").replace(" (Lnet/minecraft/world/World;IIILnet/minecraft/block/Block;I)V", "").replace(" (Lnet/minecraft/world/World;III)Lnet/minecraft/util/AxisAlignedBB;", "");
            String removed8 = removed7.replace(" (Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V", "").replace(" (Lnet/minecraft/entity/player/EntityPlayerMP;)V", "").replace(" (Lnet/minecraft/network/status/INetHandlerStatusClient;)V", "").replace(" (Lnet/minecraft/world/World;IIII)Z", "");
            String removed9 = removed8.replace(" (Ljava/lang/String;IIIZ)I", "").replace(" (FF)[F", "").replace(" (Lnet/minecraft/world/World;III)F", "").replace(" (Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z", "");
            String removed10 = removed9.replace(" (Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z", "").replace(" ()Lnet/minecraft/nbt/NBTTagCompound;", "").replace(" (Ljava/lang/String;)D", "").replace("(Lnet/minecraft/world/IBlockAccess;III)I", "");
            String removed11 = removed10.replace(" (Lnet/minecraft/item/ItemStack;)Z", "").replace(" (Ljava/util/Random;)I", "").replace(" (Lnet/minecraft/entity/player/EntityPlayer;DDDDILnet/minecraft/network/Packet;)V", "").replace(" (Lnet/minecraft/client/Minecraft;II)V", "");
            String removed12 = removed11.replace(" (Lnet/minecraft/world/World;IIILjava/util/Random;)V", "").replace(" (II)Lnet/minecraft/item/ItemStack;", "").replace(" (Ljava/io/File;Ljava/util/Collection;)V", "").replace(" (Lnet/minecraft/crash/CrashReport;)Lnet/minecraft/crash/CrashReport;", "");
            String removed13 = removed12.replace(" ([Lnet/minecraft/util/LongHashMap$Entry;)V", "").replace(" (Lnet/minecraft/world/World;IIIIFI)V", "").replace(" ()Ljava/security/KeyPair;", "").replace(" (Ljava/nio/channels/GatheringByteChannel;I)I", "");
            String removed14 = removed13.replace(" (ILjava/util/Random;I)Lnet/minecraft/item/Item;", "").replace(" (Lnet/minecraft/world/World;Lnet/minecraft/world/biome/BiomeGenBase;IIIILjava/util/Random;)V", "").replace(" (Lnet/minecraft/client/resources/IResourceManager;)V", "").replace(" ()F", "").replace(" (Lnet/minecraft/command/ICommandSender;Ljava/lang/String;)I", "");
            String removed15 = removed14.replace(" ()Lnet/minecraft/world/biome/BiomeGenBase;", "").replace(" (Ljava/io/File;II)Ljava/io/DataInputStream;", "").replace(" (II)Z", "").replace(" (Lnet/minecraft/util/ChatStyle;)Lnet/minecraft/event/ClickEvent;", "").replace(" (I)I", "");
            String removed16 = removed15.replace(" (Lnet/minecraft/network/ServerStatusResponse$PlayerCountData;)V", "").replace(" ()Lnet/minecraft/server/management/ServerConfigurationManager;", "").replace(" (Lnet/minecraft/entity/EntityLivingBase;IF)I", "").replace(" ([I)[I", "").replace(" (IIII)[I", "");
            String removed17 = removed16.replace(" (Lnet/minecraft/world/World;Ljava/util/Random;[Lnet/minecraft/block/Block;[BIID)V", "").replace(" (Lnet/minecraft/event/HoverEvent;)Lnet/minecraft/util/ChatStyle;", "").replace(" (F)I", "").replace(" (IIILjava/lang/String;)V", "").replace(" ()Lcom/google/common/collect/Multimap;", "");
            String removed18 = removed17.replace(" (Lnet/minecraft/entity/monster/EntitySpider;)Lnet/minecraft/util/ResourceLocation;", "").replace(" (Lnet/minecraft/client/audio/ISound;Lnet/minecraft/client/audio/SoundPoolEntry;)F", "").replace(" (Lnet/minecraft/block/Block;)Z", "").replace(" (Lnet/minecraft/world/World;III)Z", "");
            String removed19 = removed18.replace(" (Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/AxisAlignedBB;", "").replace(" ()Lnet/minecraft/client/multiplayer/ServerData;", "").replace(" (Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/item/EntityItem;", "").replace(" (Lnet/minecraft/world/gen/structure/StructureComponent;Ljava/util/List;Ljava/util/Random;)V", "");
            String removed20 = removed19.replace(" (Lnet/minecraft/entity/player/EntityPlayer;Z)V", "").replace(" (IIIILnet/minecraft/client/renderer/Tessellator;II)V", "").replace(" (Lnet/minecraft/world/World;Ljava/util/Random;III)Z", "").replace(" (ILjava/nio/IntBuffer;)V", "").replace(" (Lnet/minecraft/item/ItemStack;)V", "");
            String removed21 = removed20.replace(" (Ljava/lang/Object;)Z", "").replace(" (Lnet/minecraft/world/World;Ljava/util/Random;II)V", "").replace(" (II)V", "").replace(" (Lnet/minecraft/util/DamageSource;)V", "").replace(" (Lnet/minecraft/command/CommandSpreadPlayers$Position;)D", "");
            String removed22 = removed21.replace(" (CI)V", "").replace(" (Ljava/lang/String;)Lnet/minecraft/item/ItemArmor$ArmorMaterial;", "").replace(" (III)V", "").replace(" ()Lnet/minecraft/block/BlockRedstoneDiode;", "").replace(" (Ljava/util/List;Lnet/minecraft/network/PacketBuffer;)V", "");
            String removed23 = removed22.replace(" (Lnet/minecraft/util/AxisAlignedBB;)Z", "").replace(" (Lnet/minecraft/potion/PotionEffect;Z)V", "").replace(" (Lnet/minecraft/network/INetHandler;)V", "").replace(" (Lnet/minecraft/entity/passive/EntityPig;)Lnet/minecraft/util/ResourceLocation;", "").replace(" (Lnet/minecraft/client/renderer/Tessellator;FFFFFF)V", "");
            String removed24 = removed23.replace(" (ID)V", "").replace(" (Lnet/minecraft/world/storage/WorldInfo;)I", "").replace(" (Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/EntityPlayer;IFFF)Z", "").replace(" (Lnet/minecraft/network/play/server/S39PacketPlayerAbilities;)V", "").replace(" (Lnet/minecraft/network/play/INetHandlerPlayClient;)V", "");
            String removed25 = removed24.replace(" (I)V", "").replace(" (ILnet/minecraft/entity/ai/attributes/AttributeModifier;)D", "").replace(" (Lnet/minecraft/world/biome/BiomeGenBase;)Z", "").replace(" (I)Ljava/lang/Object;", "").replace(" (Lnet/minecraft/client/gui/GuiButton;)V", "");
            String removed26 = removed25.replace(" (Lnet/minecraft/tileentity/TileEntity;)Z", "").replace(" (Ljava/lang/Object;Ljava/lang/Object;)I", "").replace(" (Ljava/lang/String;C)I", "").replace(" (Lnet/minecraft/block/Block;IF)V", "").replace(" (Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V", "");
            String removed27 = removed26.replace(" (F)V", "").replace(" (ILjava/lang/CharSequence;)I", "").replace(" (IZ)Lio/netty/buffer/ByteBuf;", "").replace(" (Lnet/minecraft/client/gui/GuiCreateFlatWorld;)F", "").replace(" (I)Lnet/minecraft/item/ItemStack;", "");
            String removed28 = removed27.replace(" (FFFFFFLnet/minecraft/entity/Entity;)V", "").replace(" (Lnet/minecraft/world/World;Ljava/util/Random;III)V", "").replace(" (Lnet/minecraft/nbt/NBTTagCompound;)V", "").replace(" ([BII)Lio/netty/buffer/ByteBuf;", "");
            String removed29 = removed28.replace(" (Lnet/minecraft/network/PacketBuffer;)V", "").replace(" (Lnet/minecraft/client/renderer/entity/RenderManager;)V", "").replace(" (Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;)Ljava/util/List;", "").replace(" (Lnet/minecraft/client/gui/stream/GuiIngestServers;)Lnet/minecraft/client/Minecraft;", "");
            String removed30 = removed29.replace(" (Lnet/minecraft/network/play/server/S20PacketEntityProperties;)V", "").replace(" (Lnet/minecraft/network/play/INetHandlerPlayClient;)V", "").replace(" (Lnet/minecraft/network/INetHandler;)V", "").replace(" (Lnet/minecraft/network/PacketBuffer;)V", "");
            String removed31 = removed30.replace(" ()Lnet/minecraft/util/IIcon;", "").replace(" (Lnet/minecraft/world/gen/structure/StructureVillagePieces$Start;Ljava/util/List;Ljava/util/Random;IIIII)Lnet/minecraft/world/gen/structure/StructureVillagePieces$House2;", "");
            String removed32 = removed31.replace(" (I)Lnet/minecraft/network/NetworkStatistics$PacketStat;", "").replace(" (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/stats/StatBase;I)V", "").replace(" (Ljava/security/PrivateKey;)[B", "");
            String removed33 = removed32.replace(" (Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/EntityPlayer;IFFF)Z", "").replace(" (Lnet/minecraft/network/play/server/S2FPacketSetSlot;)V", "").replace(" (Lnet/minecraft/server/network/NetHandlerLoginServer;)Lnet/minecraft/server/MinecraftServer;", "");
            String removed34 = removed33.replace(" (Lnet/minecraft/world/IBlockAccess;III)V", "").replace(" (Lnet/minecraft/world/World;IIILnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;)V", "").replace(" (Lnet/minecraft/world/World;I)Lnet/minecraft/tileentity/TileEntity;", "");

            String[] names = removed34.split(" ");
            String leftSideEntire = names[0];
            String rightSideEntire = names[1];
            int lastSlashLeftSide = leftSideEntire.lastIndexOf("/") + 1;
            int lastSlashRightSide = rightSideEntire.lastIndexOf("/") + 1;

            String leftSide = leftSideEntire.substring(lastSlashLeftSide);
            String rightSide = rightSideEntire.substring(lastSlashRightSide);

            if (leftSide.equals("$VALUES") || rightSide.equals("$VALUES")) {
                continue;
            }

            if (lineToRead.startsWith("CL:") && !leftSide.startsWith("net/") && rightSide.startsWith("net/")) {
                MinecraftObfuscationHelper.OBFUSCATED_CLASS_TO_DEOBFUSCATED_CLASS.put(leftSide, rightSide.replace('/', '.'));
                continue;
            }
            if (rightSide.startsWith("field_") || rightSide.startsWith("func_")) {
//                LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.DEBUG, "SRG: %s, MCP: %s, Line: %s", rightSide, leftSide, entireLineWithoutFront);
                if (!MinecraftObfuscationHelper.SRG_TO_MCP_MAPPING.containsKey(rightSide)) {
                    MinecraftObfuscationHelper.SRG_TO_MCP_MAPPING.put(rightSide, leftSide);
                }
            }
        }

        //Clean-up phase
        InputStreamHelper.closeInputStream(stream);
        InputStreamHelper.closeInputStreamReader(streamReader);
        ReadingHelper.closeBufferedReader(reader);
    }

    public static final class UnableToAccessMethodException extends RuntimeException {

        private final String[] methodNames;

        public UnableToAccessMethodException(String[] methodNames, Exception e) {
            super(e);
            this.methodNames = methodNames;
        }

        public String[] getMethodNames() {
            return methodNames;
        }

    }

	public static final class ClassArray {

		private final Class[] parameters;

		private ClassArray(Class... parameters) {
			this.parameters = parameters;
		}

		public final Class[] getArray() {
			return parameters;
		}

	}

	private static final class ObjectArray {

		private final Object[] objects;

		private ObjectArray(Object... objects) {
			this.objects = objects;
		}

		public final Object[] getObjects() {
			return objects;
		}

	}

}
