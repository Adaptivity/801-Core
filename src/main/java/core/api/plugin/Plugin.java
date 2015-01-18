package core.api.plugin;

import core.Core;
import net.minecraft.util.EnumChatFormatting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * I'm sorry FML if this is too much based off of your methods.
 * I wish I had more experience to do much-much more than just this. :\
 * Without FML here, I wouldn't know what to do. You may take all of the credits FML team for this code I made.
 * @author Master801
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin {

	String name();

	String version();

	String description();

	/**
	 * The mod that 'owns' the Plugin.
	 */
	Class owner() default Core.class;

	/**
	 * Just a dummy Annotation like FML's.
	 * @author Master801
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface PluginEventHandler {
	}

	/**
	 * Input the plugin's name to make a new instance of the Plugin Class.
	 * Remember to have the field named as instance, or else the reflection won't work!
	 * @author Master801
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface PluginInstance {

		/**
		 * The plugin's name.
		 */
		String value();

	}

	/**
	 * Only applies to the Plugin's name.
	 * @author Master801
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface PluginFancyName {

		EnumChatFormatting value() default EnumChatFormatting.OBFUSCATED;

		/**
		 * @return Does have a fancy name or not.
		 */
		boolean doesHaveFancyName() default false;

	}

}
