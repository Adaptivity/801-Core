package core.common.inventory.container.test;

import core.api.client.gui.IGuiHelper;
import core.common.inventory.container.ContainerCoreBase;
import core.tileentity.test.TileEntityTestGuiAnimation;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

/**
 * Created by Master801 on 10/13/2014.
 * @author Master801
 */
public class ContainerTestGuiAnimation extends ContainerCoreBase<TileEntityTestGuiAnimation> {

    public ContainerTestGuiAnimation(InventoryPlayer playerInventory, TileEntityTestGuiAnimation inventory) {
        super(playerInventory, inventory);
    }

}