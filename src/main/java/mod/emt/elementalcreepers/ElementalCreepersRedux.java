package mod.emt.elementalcreepers;

import static mod.emt.elementalcreepers.ElementalCreepersRedux.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.emt.elementalcreepers.util.ECCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, acceptedMinecraftVersions = ACCEPTED_VERSIONS)
public class ElementalCreepersRedux {
    public static final String MOD_ID = "elementalcreepers";
    public static final String MOD_PREFIX = MOD_ID + ":";
    public static final String NAME = "Elemental Creepers Redux";
    public static final String VERSION = "1.0.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final CreativeTabs TAB = new ECCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY.length, MOD_ID + ".tab");

    @Mod.Instance
    public static ElementalCreepersRedux instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info(NAME + " initialized");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info(NAME + " post-initialized");
    }
}
