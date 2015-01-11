package core.api.pipe.transport;

import java.util.List;

import net.minecraft.item.ItemStack;
import core.api.pipe.IPipe;

/**
 * @author Master801
 */
public interface IPipeTransport extends IPipe {

	List<ItemStack> getItemsInPipe();

}
