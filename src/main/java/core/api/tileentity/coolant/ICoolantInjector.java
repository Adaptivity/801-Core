package core.api.tileentity.coolant;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidTank;

/**
 * Created by Master801 on 11/2/2014.
 * <p>
 *     Only used to cool down a valid nearby heat source.
 * </p>
 * @author Master801
 */
public interface ICoolantInjector {

    IFluidTank getCoolantTank();

    ICoolantIntake getIntakeFromSide(ForgeDirection side);

    void setIntakeFromSide(ICoolantIntake coolantIntake, ForgeDirection side);

    public static enum InjectorTypes {

        ALUMINUM(2000, "aluminium"),

        TIN(4000, "tin"),

        COPPER(8000, "copper"),

        BRONZE(14000, "bronze"),

        IRON(17000, "iron"),

        GOLD(20000, "gold"),

        STEEL(32000, "steel");

        private final int tankSize;
        private final String unlocalizedName;

        private InjectorTypes(int tankSize, String unlocalizedName) {
            this.tankSize = tankSize;
            this.unlocalizedName = "coolantInjector." + unlocalizedName;
        }

        public int getTankSize() {
            return tankSize;
        }

        public String getUnlocalizedName() {
            return unlocalizedName;
        }
    }

}
