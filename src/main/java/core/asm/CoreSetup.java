package core.asm;

import core.helpers.MinecraftObfuscationHelper;
import cpw.mods.fml.relauncher.IFMLCallHook;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.util.Map;

/**
 * Created by Master801 on 1/13/2015 at 8:18 PM.
 * @author Master801
 */
public final class CoreSetup implements IFMLCallHook {

    private static LaunchClassLoader classLoader;

    @Override
    public void injectData(Map<String, Object> data) {
        classLoader = (LaunchClassLoader)data.get("classLoader");
    }

    @Override
    public Void call() throws Exception {//This method actually sets up stuff.
        MinecraftObfuscationHelper.setupMappings("/assets/core/mappings/mcp&srg.mapping");//Setup the mappings.
        return null;//Always return null.
    }

    public static LaunchClassLoader getClassLoader() {
        return classLoader;
    }

}
