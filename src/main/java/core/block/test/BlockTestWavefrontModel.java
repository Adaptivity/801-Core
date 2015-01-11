package core.block.test;

import core.block.BlockCoreBase;
import core.tileentity.test.TileEntityTestWavefrontModel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Master801 on 12/14/2014 at 1:07 PM.
 * @author Master801
 */
public class BlockTestWavefrontModel extends BlockCoreBase {

    public BlockTestWavefrontModel() {
        super(Material.iron, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void registerIcon(TextureMap map) {
        blockIcon = Blocks.command_block.getIcon(0, 0);
    }

    @Override
    public String getAdjustedUnlocalizedName() {
        return "test.wavefront";
    }

    @Override
    public TileEntity createTileEntity(int metadata) {
        return new TileEntityTestWavefrontModel();
    }

}
