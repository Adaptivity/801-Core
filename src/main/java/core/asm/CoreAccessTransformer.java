package core.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

/**
 * Created by Master801 on 12/29/2014 at 11:39 AM.
 * @author Master801
 */
public final class CoreAccessTransformer extends AccessTransformer {

    public CoreAccessTransformer() throws IOException {
        super("META-INF/core_AT.cfg");
    }

}
