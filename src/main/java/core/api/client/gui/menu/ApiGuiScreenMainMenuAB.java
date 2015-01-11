package core.api.client.gui.menu;

public abstract class ApiGuiScreenMainMenuAB {

	/**
	 * Use this wisely...
	 * @author Master801
	 */
	public static ApiGuiScreenMainMenuAB instance = null;

	public abstract void addStringToSplashText(String text);

	public abstract void setPanoramaFile(String panoramaFile);

	public abstract String getPanoramaFile();

	public abstract Object[] getAllSplashTexts();

}
