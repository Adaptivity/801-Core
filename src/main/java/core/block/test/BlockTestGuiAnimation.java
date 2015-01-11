package core.block.test;

import core.block.BlockCoreBase;
import core.helpers.SpriteHelper;
import core.tileentity.test.TileEntityTestGuiAnimation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Master801 on 10/13/2014.
 * @author Master801
 */
public class BlockTestGuiAnimation extends BlockCoreBase {

    public BlockTestGuiAnimation() {
        super(Material.iron, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcon(TextureMap map) {
        blockIcon = SpriteHelper.createNewSpriteSheetAtlas("/resources/textures/blocks.png", "grate", map, 4, 2);
    }

    /**
     * Do not remove this method.
     * This for setting the unlocalized name.
     * Which you actually can't set in the constructor. :\
     * This may seem redundant, but it's worth it.
     */
    @Override
    public String getAdjustedUnlocalizedName() {
        return "801-Core.block.test.guiAnimation";
    }

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityTestGuiAnimation();
    }

}
