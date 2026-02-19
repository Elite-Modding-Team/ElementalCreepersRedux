package mod.emt.elementalcreepers.init;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.client.render.*;
import mod.emt.elementalcreepers.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;

@Mod.EventBusSubscriber(modid = ElementalCreepersRedux.MOD_ID)
public class ECEntities {
    private static int entityID = 1;

    public static void registerEntity(String name, Class<? extends Entity> clazz, int eggColor1, int eggColor2) {
        EntityRegistry.registerModEntity(new ResourceLocation(ElementalCreepersRedux.MOD_ID, name), clazz, ElementalCreepersRedux.MOD_ID + "." + name, entityID++, ElementalCreepersRedux.instance, 64, 1, true, eggColor1, eggColor2);
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz) {
        EntityRegistry.registerModEntity(new ResourceLocation(ElementalCreepersRedux.MOD_ID, name), clazz, ElementalCreepersRedux.MOD_ID + "." + name, entityID++, ElementalCreepersRedux.instance, 64, 1, true);
    }

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event) {
        ElementalCreepersRedux.LOGGER.info("Registering entities...");

        registerEntity("cookie_creeper", ECEntityCookieCreeper.class, 13011540, 8342837);
        registerEntity("dark_creeper", ECEntityDarkCreeper.class, 3421236, Color.WHITE.getRGB());
        registerEntity("earth_creeper", ECEntityEarthCreeper.class, 7224073, 7396445);
        registerEntity("electric_creeper", ECEntityElectricCreeper.class, 16772666, Color.BLACK.getRGB());
        registerEntity("fire_creeper", ECEntityFireCreeper.class, 16740369, Color.BLACK.getRGB());
        registerEntity("friendly_creeper", ECEntityFriendlyCreeper.class, 14183121, Color.BLACK.getRGB());
        registerEntity("ghost_creeper", ECEntityGhostCreeper.class, 7644532, 1118481);
        registerEntity("illusion_creeper", ECEntityIllusionCreeper.class, 12303291, 7434609);
        registerEntity("light_creeper", ECEntityLightCreeper.class, 16774535, 9800765);
        registerEntity("magma_creeper", ECEntityMagmaCreeper.class, 12006990, Color.BLACK.getRGB());
        registerEntity("psychic_creeper", ECEntityPsychicCreeper.class, 10180028, Color.BLACK.getRGB());
        registerEntity("reverse_creeper", ECEntityReverseCreeper.class, Color.BLACK.getRGB(), 894731);
        registerEntity("spider_creeper", ECEntitySpiderCreeper.class, 2493707, 11013646);
        registerEntity("water_creeper", ECEntityWaterCreeper.class, 5603516, Color.BLACK.getRGB());
        registerEntity("winter_creeper", ECEntityWinterCreeper.class, 13816530, Color.BLACK.getRGB());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(@Nonnull final ModelRegistryEvent event) {
        ElementalCreepersRedux.LOGGER.info("Registering entity renderers...");

        RenderingRegistry.registerEntityRenderingHandler(ECEntityCookieCreeper.class, new ECRenderCookieCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityDarkCreeper.class, new ECRenderDarkCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityEarthCreeper.class, new ECRenderEarthCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityElectricCreeper.class, new ECRenderElectricCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityFireCreeper.class, new ECRenderFireCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityFriendlyCreeper.class, new ECRenderFriendlyCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityGhostCreeper.class, new ECRenderGhostCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityIllusionCreeper.class, new ECRenderIllusionCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityLightCreeper.class, new ECRenderLightCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityMagmaCreeper.class, new ECRenderMagmaCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityPsychicCreeper.class, new ECRenderPsychicCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityReverseCreeper.class, new ECRenderReverseCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntitySpiderCreeper.class, new ECRenderSpiderCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityWaterCreeper.class, new ECRenderWaterCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityWinterCreeper.class, new ECRenderWinterCreeper.Factory());
    }
}
