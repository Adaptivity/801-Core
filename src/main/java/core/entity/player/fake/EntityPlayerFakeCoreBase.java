package core.entity.player.fake;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * @author Master801
 */
public class EntityPlayerFakeCoreBase extends EntityPlayer {

	protected final World world;
	protected final ChunkCoordinates coords;

	public EntityPlayerFakeCoreBase(World world, String username, ChunkCoordinates coords) {
		super(world, new GameProfile(UUID.fromString(username), username));
		this.world = world;
		this.coords = coords;
	}

	@Override
	public final void addChatMessage(IChatComponent message) {
	}

	@Override
	public final boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
		return false;
	}

	@Override
	public final ChunkCoordinates getPlayerCoordinates() {
		return coords;
	}

	@Override
	public final boolean canEat(boolean p_71043_1_) {
		return false;
	}

	@Override
	public final boolean shouldHeal() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final boolean getAlwaysRenderNameTagForRender() {
		return false;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return super.canHarvestBlock(block);
	}

}
