package mod.emt.elementalcreepers.init;

import javax.annotation.Nonnull;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ElementalCreepersRedux.MOD_ID)
public class ECRegistry {
    @SubscribeEvent
    public static void registerSoundEvents(@Nonnull final RegistryEvent.Register<SoundEvent> event) {
        final IForgeRegistry<SoundEvent> registry = event.getRegistry();

        for (ECSoundEvents soundEvents : ECSoundEvents.values()) {
            registry.register(soundEvents.getSoundEvent());
        }
    }
}
