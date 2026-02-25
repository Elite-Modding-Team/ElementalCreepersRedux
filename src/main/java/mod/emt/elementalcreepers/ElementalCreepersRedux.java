package mod.emt.elementalcreepers;

import static mod.emt.elementalcreepers.ElementalCreepersRedux.*;

import mod.emt.elementalcreepers.config.ECConfigLists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, acceptedMinecraftVersions = ACCEPTED_VERSIONS)
public class ElementalCreepersRedux {
    public static final String MOD_ID = "elementalcreepers";
    public static final String MOD_PREFIX = MOD_ID + ":";
    public static final String NAME = "Elemental Creepers Redux";
    public static final String VERSION = "1.0.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.Instance
    public static ElementalCreepersRedux instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info(NAME + " initialized");
        ECConfigLists.initLists();
    }
}
