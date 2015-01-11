package core.world.generation;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public abstract class WorldOreGenerationCoreBase implements IWorldGenerator {

    protected WorldOreGenerationCoreBase() {
    }

    /**
     * Generate some world
     *
     * @param random         the chunk specific {@link java.util.Random}.
     * @param chunkX         the chunk X coordinate of this chunk.
     * @param chunkZ         the chunk Z coordinate of this chunk.
     * @param world          : additionalData[0] The minecraft {@link net.minecraft.world.World} we're generating for.
     * @param chunkGenerator : additionalData[1] The {@link net.minecraft.world.chunk.IChunkProvider} that is generating.
     * @param chunkProvider  : additionalData[2] {@link net.minecraft.world.chunk.IChunkProvider} that is requesting the world generation.
     */
    @Override
    public final void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int dimensionID = world.provider.dimensionId;
        switch (dimensionID) {
            case -1:
                generateNetherOre(world, chunkX, chunkZ, random);
                break;
            case 0:
                generateSurfaceOre(world, chunkX, chunkZ, random);
                break;
            case 1:
                generateEnderOre(world, chunkX, chunkZ, random);
                break;
            default:
                generateCustomOre(dimensionID, world, chunkX, chunkZ, random);
                break;
        }
    }

    /**
     * Override to generate ore in the over-world.
     */
    protected void generateSurfaceOre(World world, int chunkX, int chunkZ, Random random) {
    }

    /**
     * Override to generate ore in the nether. (Make sure to add an extra parameter in WorldGenMinable!)
     */
    protected void generateNetherOre(World world, int chunkX, int chunkZ, Random random) {
    }

    /**
     * Override to generate ore in the End. (Make sure to add an extra parameter in WorldGenMinable!)
     */
    protected void generateEnderOre(World world, int chunkX, int chunkZ, Random random) {
    }

    /**
     * Override to generate ore in a custom dimension. (Make sure to add an extra parameter in WorldGenMinable!)
     */
    protected void generateCustomOre(int dimensionID, World world, int chunkX, int checkZ, Random random) {
    }

}
