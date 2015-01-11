package core.helpers;

import core.Core;
import core.common.resources.CoreEnums;
import core.helpers.ReflectionHelper.ClassParameters;
import core.helpers.ReflectionHelper.ObjectParameters;
import net.minecraft.block.material.MapColor;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom map thingys for more custom map things.
 * @author Master801
 */
public final class MapColourHelper {

	public static boolean hasInitColourInjections = false;
    private static final Map<Color, MapColor> mapcolourMapping = new HashMap<Color, MapColor>();

	/**
	 * <p>
	 *     <b>
	 *         This should only be used internally. May cause errors if it is not internally used.
	 *     </b>
	 * </p>
	 */
	static void initMapColourInjections() {
		if (MapColourHelper.hasInitColourInjections) {
			return;
		}
		Field field = MinecraftObfuscationHelper.getMinecraftFieldValue(MapColor.class, "field_76281_a", "mapColorArray");
		Map<Integer, MapColor> oldColours = new HashMap<Integer, MapColor>();
		for(int i = 0; i < MapColor.mapColorArray.length; i++) {
			MapColor colour = MapColor.mapColorArray[i];
			if (colour != null) {
				oldColours.put(i, colour);
			}
		}
        boolean prevAccessible = field.isAccessible();
        if (!prevAccessible) {
            field.setAccessible(true);
        }
		if (((MapColor[])ReflectionHelper.getFieldValue(field, null)).length <= 64) {//Checks if the limitation is there. If it is, set the new value.
			ReflectionHelper.setFinalFieldValue(field, null, new MapColor[1024]);//Change the value to something suitable, and useful.
		}
		if (!oldColours.isEmpty()) {
			for(int i = 0; i < oldColours.size(); i++) {
				MapColor.mapColorArray[i] = oldColours.get(i);
			}
		}
        field.setAccessible(prevAccessible);
		MapColourHelper.hasInitColourInjections = true;
	}

	/**
	 * Creates a new instance of MapColor with the specified colour.
	 * <p>
	 * Note, this does not edit base-classes. Instead it uses Java Reflection to add a new MapColor into the MapColor mapping.
	 * <s>This only allows 64 Map Colors to be allocated.</s>
	 * </p>
	 * <p>
	 * <s>So technically, you only get 29 different options to choose from.
	 * Use the colours wisely...
	 * </s>
	 * </p>
	 */
	public static MapColor createNewMapColour(Color colour) {
		if (!MapColourHelper.hasInitColourInjections) {
			MapColourHelper.initMapColourInjections();
		}
		MapColor color = mapcolourMapping.get(colour);
        if (color != null) {
            return color;
        }
		int nextID = -1;
		for(int i = 0; i < MapColor.mapColorArray.length; i++) {
			if (MapColor.mapColorArray[i] == null && nextID == -1) {
				nextID = i;
			}
		}
		if (nextID == -1) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.ERROR, "Failed to make a new MapColour! Colour: %s", colour.toString());
			return MapColor.cyanColor;
		}
		color = ReflectionHelper.createNewObjectFromConstructor((ReflectionHelper.getConstructor(MapColor.class, new ClassParameters(int.class, int.class))), new ObjectParameters(nextID, ColourHelper.convertColourToRGB(colour)));
		if (!mapcolourMapping.containsKey(colour) && !mapcolourMapping.containsValue(color)) {
            mapcolourMapping.put(colour, color);
        }
        LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.INFO, "Successfully added a new MapColour! Colour: %s, MapColour: %s", colour.toString(), color.toString());
        return color;
	}

    static {
        if (!MapColourHelper.hasInitColourInjections) {
            MapColourHelper.initMapColourInjections();
            MapColourHelper.hasInitColourInjections = true;
        }
    }

}
