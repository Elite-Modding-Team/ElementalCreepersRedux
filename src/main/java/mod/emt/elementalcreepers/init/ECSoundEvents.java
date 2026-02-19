package mod.emt.elementalcreepers.init;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public enum ECSoundEvents {
    RANDOM_EXPLOSION_CLASSIC("random.explosion_classic");

    private final SoundEvent soundEvent;

    ECSoundEvents(String path) {
        ResourceLocation resourceLocation = new ResourceLocation(ElementalCreepersRedux.MOD_ID, path);
        this.soundEvent = new SoundEvent(resourceLocation);
        this.soundEvent.setRegistryName(resourceLocation);
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }
}
