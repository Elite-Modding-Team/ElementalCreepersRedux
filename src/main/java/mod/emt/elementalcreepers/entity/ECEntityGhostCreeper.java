package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import mod.emt.elementalcreepers.misc.EntityOnlyExplosion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ECEntityGhostCreeper extends ECEntityElementalCreeper {
    public ECEntityGhostCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.GHOST_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.GHOST_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.GHOST_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.GHOST_CREEPER.explosionRadius;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        EntityOnlyExplosion.explodeAt(this.world, this, this.posX, this.posY, this.posZ,
                radius, 3.0, 1.0D
        );

        /*if (!this.world.isRemote && this.world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) this.world;
            worldServer.spawnParticle(EnumParticleTypes.END_ROD, this.posX, this.posY, this.posZ, 30, 1.0, 1.0, 1.0, 0.05);
        }*/

        if (!ECConfig.ENTITIES.GHOST_CREEPER.classicExplosionSound) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.HOSTILE, 2.0F, (2.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
        }
        handleNetworkedExplosionEffects(radius, ECConfig.ENTITIES.GHOST_CREEPER.classicExplosionSound ? ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent() : SoundEvents.ENTITY_GENERIC_EXPLODE);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.GHOST_CREEPER;
    }
}
