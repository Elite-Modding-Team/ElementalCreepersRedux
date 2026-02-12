package mod.emt.elementalcreepers.entity;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ECEntityElementalCreeper extends EntityCreeper {
    protected int oldSwell;
    protected int swell;
    protected int maxSwell = 30;

    public ECEntityElementalCreeper(World world) {
        super(world);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        this.swell += (int) (distance * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getSwelling(float partialTicks) {
        return (this.oldSwell + (this.swell - this.oldSwell) * partialTicks) / (float) (this.maxSwell - 2);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setShort("Fuse", (short) this.maxSwell);
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("Fuse", 99)) {
            this.maxSwell = compound.getShort("Fuse");
        }
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.oldSwell = this.swell;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                if (!this.world.isRemote) {
                    this.isDead = true;
                    this.creeperEffect();
                    this.setDead(); // Equivalent to remove()
                }
            }
        }
        super.onUpdate();
    }

    // TODO: Figure out what this was for
    protected void creeperEffect() {
        // float f = this.isPowered() ? 2.0F : 1.0F;
        // this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)
        // this.explosionRadius * f,
        // Level.ExplosionInteraction.MOB);
        // this.spawnLingeringCloud();
    }

    protected void spawnLingeringCloud() {
        Collection<PotionEffect> collection = this.getActivePotionEffects();
        if (!collection.isEmpty()) {
            EntityAreaEffectCloud cloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());

            for (PotionEffect potioneffect : collection) {
                cloud.addEffect(new PotionEffect(potioneffect));
            }

            this.world.spawnEntity(cloud);
        }
    }

    protected void handleNetworkedExplosionEffects(double radius, SoundEvent soundEvent) {
        handleNetworkedExplosionEffects(radius, null, soundEvent);
    }

    protected void handleNetworkedExplosionEffects(double radius, @Nullable Map<EntityPlayer, Vec3d> hitPlayers, SoundEvent soundEvent) {
        double x = this.posX;
        double y = this.posY;
        double z = this.posZ;

        if (!this.world.isRemote) {
            this.world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
        }


        if (!this.world.isRemote && this.world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) this.world;
            SPacketExplosion packet = new SPacketExplosion(x, y, z, (float) radius, new ArrayList<BlockPos>(), null);

            for (EntityPlayerMP player : worldServer.getPlayers(EntityPlayerMP.class, p -> p.getDistanceSq(x, y, z) < 4096.0D)) {
                if (hitPlayers != null && hitPlayers.containsKey(player)) {
                    SPacketExplosion playerPacket = new SPacketExplosion(x, y, z, (float) radius, new ArrayList<BlockPos>(), hitPlayers.get(player));
                    player.connection.sendPacket(playerPacket);
                } else {
                    player.connection.sendPacket(packet);
                }
            }
        }

        this.spawnLingeringCloud();
    }

    /*@Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (!this.world.isRemote && this.world.rand.nextDouble() < Config.ghostCreeperSpawnChance
                && !(this instanceof GhostCreeper)) {
            EntityGhostCreeper ghost = new EntityGhostCreeper(this.world);
            ghost.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.world.spawnEntity(ghost);
        }
    }*/
}
