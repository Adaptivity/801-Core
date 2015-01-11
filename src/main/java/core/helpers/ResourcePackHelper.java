package core.helpers;

import core.common.resources.CoreResources;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourcePack;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Master801 on 12/2/2014 at 9:34 PM.
 * @author Master801
 */
public final class ResourcePackHelper {

    public static InputStream getInputStreamFromCurrentPack(String resourcePath) {
        IResourcePack resourcePack = CoreResources.getMinecraft().getResourcePackRepository().func_148530_e();//Gets the current resource pack.
        ZipFile zippy = null;
        boolean skipAbstract = false;
        if (resourcePack instanceof FileResourcePack) {
            zippy = MinecraftObfuscationHelper.getMinecraftFieldValue(FileResourcePack.class, (FileResourcePack)resourcePack, "field_110600_d", "resourcePackZipFile");
            skipAbstract = true;
        }
        if (!skipAbstract && resourcePack instanceof AbstractResourcePack) {
            AbstractResourcePack abstractResourcePack = (AbstractResourcePack)resourcePack;
            File resourcePackFile = MinecraftObfuscationHelper.getMinecraftFieldValue(AbstractResourcePack.class, abstractResourcePack, "field_110597_b", "resourcePackFile");
            if (resourcePackFile != null) {
                try {
                    zippy = new ZipFile(resourcePackFile);//No possible way of getting around this without catching invisible errors when invoking methods.
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        if (zippy != null) {
            ZipEntry entry = zippy.getEntry(resourcePath);
            if (entry != null) {
                return InputStreamHelper.openStreamFromEntry(zippy, entry);
            }
        }
        return null;
    }

}
