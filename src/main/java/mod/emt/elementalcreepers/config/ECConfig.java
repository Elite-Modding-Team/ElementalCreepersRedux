package mod.emt.elementalcreepers.config;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ElementalCreepersRedux.MOD_ID, name = "ElementalCreepersRedux")
public class ECConfig {
    @Config.LangKey("cfg.elementalcreepers.general")
    @Config.Name("General")
    public static final GeneralSettings GENERAL = new GeneralSettings();

    public static class GeneralSettings {
        /*@Config.Name("Debug Mode")
        @Config.Comment("Prints debug values to console")
        public boolean debugMode = false;*/
    }

    @Mod.EventBusSubscriber(modid = ElementalCreepersRedux.MOD_ID)
    public static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ElementalCreepersRedux.MOD_ID)) {
                ConfigManager.sync(ElementalCreepersRedux.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
