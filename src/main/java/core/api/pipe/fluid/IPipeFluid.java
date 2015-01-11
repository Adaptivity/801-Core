package core.api.pipe.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import core.api.pipe.IPipe;

/**
 * @author Master801
 */
public interface IPipeFluid extends IPipe, IFluidHandler {

	FluidStack getFluidStackInPipe();

}
