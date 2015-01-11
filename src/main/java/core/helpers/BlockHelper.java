package core.helpers;

import net.minecraft.block.Block;

/**
 * Created by Master801 on 10/12/2014.
 * @author Master801
 */
public final class BlockHelper {

    /**
     * Not your usual registered name from a block.
     * @return The name the block is using to register with.
     */
    public static String getRegsiteredNameFromBlock(Block block) {
        if (block == null) {
            return null;
        }
        return Block.blockRegistry.getNameForObject(block);//Some-what hacky.
    }

}
