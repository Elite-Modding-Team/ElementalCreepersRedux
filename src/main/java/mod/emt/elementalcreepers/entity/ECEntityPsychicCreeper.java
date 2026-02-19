package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ECEntityPsychicCreeper extends ECEntityElementalCreeper {
    public ECEntityPsychicCreeper(World world) {
        super(world);
    }

    @Override
    public void creeperEffect() {
        double radius = 3.0;

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
                double strength = 5.0;
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

        handleNetworkedExplosionEffects(radius, SoundEvents.ENTITY_SHULKER_SHOOT);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.PSYCHIC_CREEPER;
    }
}
