package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ECEntityPsychicCreeper extends ECEntityElementalCreeper {
    public ECEntityPsychicCreeper(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.PSYCHIC_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.PSYCHIC_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.PSYCHIC_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.PSYCHIC_CREEPER.explosionRadius;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        List<EntityLivingBase> targets = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(radius));
        for (EntityLivingBase entity : targets) {
            if (entity == this) continue;

            double dX = entity.posX - this.posX;
            double dY = entity.posY - this.posY;
            double dZ = entity.posZ - this.posZ;
            double dist = MathHelper.sqrt(dX * dX + dY * dY + dZ * dZ);

            if (dist > 0) {
                double strength = ECConfig.ENTITIES.PSYCHIC_CREEPER.explosionKnockbackStrength;
                double pushX = (dX / dist) * strength;
                double pushY = 0.5D * strength;
                double pushZ = (dZ / dist) * strength;

                entity.motionX = pushX;
                entity.motionY = pushY;
                entity.motionZ = pushZ;

                if (entity instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) entity;
                    player.velocityChanged = true;
                    player.connection.sendPacket(new SPacketEntityVelocity(player.getEntityId(), pushX, pushY, pushZ));
                }
            }
        }

        handleNetworkedExplosionEffects(radius, ECConfig.ENTITIES.PSYCHIC_CREEPER.classicExplosionSound ? ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent() : SoundEvents.ENTITY_SHULKER_SHOOT);
    }

    @Override
    public boolean getCanSpawnHere() {
        return ECConfig.ENTITIES.PSYCHIC_CREEPER.surfaceSpawning ? super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this)) : super.getCanSpawnHere();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.PSYCHIC_CREEPER;
    }
}
