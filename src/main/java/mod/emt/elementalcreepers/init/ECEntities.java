package mod.emt.elementalcreepers.init;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.client.render.ECRenderCookieCreeper;
import mod.emt.elementalcreepers.client.render.ECRenderFireCreeper;
import mod.emt.elementalcreepers.entity.ECEntityCookieCreeper;
import mod.emt.elementalcreepers.entity.ECEntityFireCreeper;
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

        registerEntity("cookie_creeper", ECEntityCookieCreeper.class, 2829099, 14079702);
        registerEntity("fire_creeper", ECEntityFireCreeper.class, 2829099, 14079702);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(@Nonnull final ModelRegistryEvent event) {
        ElementalCreepersRedux.LOGGER.info("Registering entity renderers...");

        RenderingRegistry.registerEntityRenderingHandler(ECEntityCookieCreeper.class, new ECRenderCookieCreeper.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ECEntityFireCreeper.class, new ECRenderFireCreeper.Factory());
    }
}
