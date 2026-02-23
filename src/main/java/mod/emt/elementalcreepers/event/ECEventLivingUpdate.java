package mod.emt.elementalcreepers.event;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.config.ECConfigLists;
import mod.emt.elementalcreepers.entity.ECEntityGhostCreeper;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ElementalCreepersRedux.MOD_ID)
public class ECEventLivingUpdate {
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        double chance = ECConfig.ENTITIES.GHOST_CREEPER.deathSpawningChance;
        if (chance <= 0) return;

        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;

        if (!world.isRemote && entity.deathTime == 19) {
            ResourceLocation location = EntityList.getKey(entity);
            if (location == null) return;

            boolean inList = ECConfigLists.creepers.stream().anyMatch(entry -> Objects.equals(entry.getRegistryName(), location));
            boolean isWhiteListMode = ECConfig.ENTITIES.GHOST_CREEPER.deathSpawningListMode == ECConfig.EnumLists.WHITELIST;
            boolean allowedToSpawn = isWhiteListMode == inList;

            if (allowedToSpawn && entity instanceof EntityCreeper) {
                if (world.rand.nextFloat() < chance) {
                    world.playSound(null, entity.posX, entity.posY, entity.posZ, ECSoundEvents.RANDOM_GHOST_SPAWN.getSoundEvent(), SoundCategory.AMBIENT, 1.0F, 0.8F + (world.rand.nextFloat() * 0.4F));
                    spawnGhostCreeper(world, entity.getPositionVector());
                }
            }
        }
    }

    public static void spawnGhostCreeper(World world, Vec3d pos) {
        ECEntityGhostCreeper ghost = new ECEntityGhostCreeper(world);
        ghost.setLocationAndAngles(pos.x, pos.y, pos.z, 0.0F, 0.0F);
        world.spawnEntity(ghost);
    }
}
