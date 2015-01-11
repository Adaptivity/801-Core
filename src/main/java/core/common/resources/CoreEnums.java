package core.common.resources;

import org.apache.logging.log4j.Level;

/**
 * @author Master801
 */
public final class CoreEnums {

    /**
     * @author Master801
     */
    public static enum MiningToolHelper {

        SHOVEL("shovel"),

        AXE("axe"),

        PICKAXE("pickaxe");

        private final String tool;

        private MiningToolHelper(String tool) {
            this.tool = tool;
        }

        public String getTool() {
            return tool;

        }

    }

    /**
     * @author Master801
     */
    public static enum MiningLevelHelper {

        WOOD(0),

        STONE(1),

        IRON(2),

        GOLD(3),

        DIAMOND(4);

        private final int miningLevel;

        private MiningLevelHelper(int miningLevel) {
            this.miningLevel = miningLevel;
        }

        public int getMiningLevel() {
            return miningLevel;
        }

    }

    /**
     * Helps when you need to make the class, Level in a switch statement.
     * @author Master801
     */
    public static enum LoggerEnum {

        OFF("OFF"),

        FATAL("FATAL"),

        ERROR("ERROR"),

        WARN("WARN"),

        INFO("INFO"),

        DEBUG("DEBUG"),

        TRACE("TRACE"),

        ALL("ALL");

        private final String loggerName;

        private LoggerEnum(String name) {
            this.loggerName = name;
        }

        public String getLoggerName() {
            return loggerName;
        }

        public Level getLevel() {
            return Level.toLevel(loggerName);
        }

    }

    /**
     * @author Master801
     */
    public static enum ArmourTypes {

        HELMET(0),

        CHEST_PLATE(1),

        LEGGINGS(2),

        BOOTS(3);

        private final int slotNumber;

        private ArmourTypes(int slotNumber) {
            this.slotNumber = slotNumber;
        }

        public int getSlotNumber() {
            return slotNumber;
        }

    }

}
