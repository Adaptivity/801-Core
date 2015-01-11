package core.api.item.armour;

import core.common.resources.CoreEnums.ArmourTypes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * Created by Master801 on 10/27/2014.
 * @author Master801
 */
public interface IArmour {

    ArmourTypes getArmourType(int metadata);

    @SideOnly(Side.CLIENT)
    ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, int armourSlot);

}
