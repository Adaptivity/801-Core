package core.common.integration.othermods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.api.common.mod.IMod;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import core.exceptions.CoreExceptions.CoreNullPointerException;

/**
 * Welcome to the Shad MultiCore.
 * @author Jeremy
 */
public final class DimensionalPattern {

    private static final Map<IMod, Map<String, DimensionalPattern>> DIMENSIONAL_MAP = new HashMap<IMod, Map<String, DimensionalPattern>>();

	private final Map<Character, BlockState> comparisonMap = new HashMap<Character, BlockState>();
	private final List<Layer> layers = new ArrayList<Layer>();

	private DimensionalPattern() {
	}

	/**
	 * Don't reference the constructor directly, instead use {@link #createPattern(IMod, String, IPatternComponent...)}.
	 */
	private DimensionalPattern(IPatternComponent... components) {
		for (IPatternComponent component : components) {
			if (component instanceof Layer) {
				layers.add((Layer) component);
			}
			if (component instanceof BlockState) {
				BlockState blockState = (BlockState) component;
				comparisonMap.put(blockState.getType(), blockState);
			}
		}
	}

	/**
	 * This method reuses same id patterns, thus reducing both duplicates in the system, and creates an easy way to get them. If you wish to replace/update a pattern, use updatePattern();
	 *
     * @param mod
     *            The mod the pattern will be registering with.
     * @param id
	 *            The id the method will either store, if it hasn't been used, or call it from the list.
	 * @param components
	 *            The pattern contents
	 */
	public static DimensionalPattern createPattern(IMod mod, String id, IPatternComponent... components) {
        Map<String, DimensionalPattern> internalMapping = DIMENSIONAL_MAP.get(mod);
        if (internalMapping == null) {
            internalMapping = new HashMap<String, DimensionalPattern>();
            DIMENSIONAL_MAP.put(mod, internalMapping);
        }
        if (internalMapping.containsKey(id)) {
            return internalMapping.get(id);
        }
		if (components.length == 0) {
			throw new CoreNullPointerException("Components are either null, or empty (meaning null)!");
		}
		DimensionalPattern pattern = new DimensionalPattern(components);
        internalMapping.put(id, pattern);
        DIMENSIONAL_MAP.put(mod, internalMapping);
        return pattern;
	}

	/**
	 * Used to update a pattern, without calling back a previous one. Don't really know why you would need this, but just in case. ;)
	 */
	public static DimensionalPattern updatePattern(IMod mod, String id, IPatternComponent... components) {
		if (components.length == 0) {
			return null;
		}
		DimensionalPattern pattern = new DimensionalPattern(components);
        Map<String, DimensionalPattern> internalMapping = DIMENSIONAL_MAP.get(mod);
        if (internalMapping == null) {
            internalMapping = new HashMap<String, DimensionalPattern>();
            DIMENSIONAL_MAP.put(mod, internalMapping);
        }
        if (internalMapping.containsKey(id)) {
            throw new CoreNullPointerException("Already contains the specified key! Key: '%s'.", id);
		}
        internalMapping.put(id, pattern);
        DIMENSIONAL_MAP.put(mod, internalMapping);
		return pattern;
	}

	/**
	 * Used to determine if the pattern has formed, starts from the lowest possible point, so give it the corner.
	 * EG, if it was the core of a 3x3x3, hasFormed(worldObj, xCoord - 1, yCoord - 1, zCoord - 1);
	 */
	public boolean hasFormed(IBlockAccess world, int x, int y, int z) {
		if (world == null) {
			return false;
		}
		int layerPos = 0;
		for (Layer layer : layers) {
			int rowPos = 0;
			for (Row row : layer.rows) {
				for (int depth = 0; depth < row.sections.length(); depth++) {
					char type = row.sections.charAt(depth);
					if (type == '*') {
						continue;
					}
					if (type == ' ') {
						if (!world.isAirBlock(x + rowPos, y + layerPos, z + depth)) {
							return false;
						}
						continue;
					}
					BlockState blockState = comparisonMap.get(type);
					if (blockState == null) {
						return false;
					}
					if (!blockState.doesMatch(world, x + rowPos, y + layerPos, z + depth)) {
						return false;
					}
				}
				rowPos++;
			}
			layerPos++;
		}
		return true;
	}

	/**
	 * Accessor Methods
	 */
	public static Layer createLayer(Row... rows) {
		return new Layer(rows);
	}

	/**
	 * Accessor Methods
	 */
	public static Row createRow(String section) {
		return new Row(section);
	}

	public static BlockState createBlockState(char character, Block block, int metadata) {
		return new BlockState(character, block, metadata);
	}

	public static final class Layer implements IPatternComponent {

		private final Row[] rows;

		private Layer(Row... rows) {
			this.rows = rows;
		}

		public Row[] getRows() {
			return rows;
		}

	}

	public static final class Row {

		private final String sections;

		private Row(String sections) {
			this.sections = sections;
		}

		public String getSections() {
			return sections;
		}

	}

	public static final class BlockState implements IPatternComponent {

		private final char type;
		private final Block block;
		private final int meta;

		public BlockState(char type, Block block, int meta) {
			this.type = type;
			this.block = block;
			this.meta = meta;
		}

		public char getType() {
			return type;
		}

		public boolean doesMatch(IBlockAccess world, int xCoord, int yCoord, int zCoord) {
			return world.getBlock(xCoord, yCoord, zCoord).equals(block) && world.getBlockMetadata(xCoord, yCoord, zCoord) == meta;
		}

	}

	private interface IPatternComponent {
	}

}
