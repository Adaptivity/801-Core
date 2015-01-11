package core.api.common.mod;

/**
 * Used for getting the instance of the mod, without actually using the Object parameter.
 * The really bugged me, so I decided to change it up by using an interface.
 * @author Master801
 */
public interface IMod extends IBase {

	String getModID();

	/**
	 * Gets the mod's instance.
	 */
	IMod getInstance();

}
