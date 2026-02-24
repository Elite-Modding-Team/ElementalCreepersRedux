package mod.emt.elementalcreepers.entity;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ECEntityElementalCreeper extends EntityCreeper {
    // We're ignoring timeSinceIgnited for this
    private int timeSinceIgnitedCustom;

    public ECEntityElementalCreeper(World world) {
        super(world);
    }

    // Very hacky but it's a way to get around the default explosions
    @Override
    public void onUpdate() {
        super.onUpdate();

        // Set it to 1 so the original code never fires
        this.timeSinceIgnited = 1;
        if (this.isEntityAlive()) {
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int state = this.getCreeperState();

            if (state > 0 && this.timeSinceIgnitedCustom == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.lastActiveTime = this.timeSinceIgnitedCustom;
            this.timeSinceIgnitedCustom += state;

            if (this.timeSinceIgnitedCustom < 0) {
                this.timeSinceIgnitedCustom = 0;
            }

            if (this.timeSinceIgnitedCustom >= this.fuseTime) {
                this.timeSinceIgnitedCustom = this.fuseTime;

                if (!this.world.isRemote) {
                    this.creeperEffect();
                    this.setDead();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getCreeperFlashIntensity(float partialTicks) {
        // Override so the new timeSinceIgnitedCustom is utilized
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnitedCustom - this.lastActiveTime) * partialTicks) / (float) (this.fuseTime - 2);
    }

    protected void creeperEffect() {
        // Creepers that extend this class implement their own effects here
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
            SPacketExplosion packet = new SPacketExplosion(x, y, z, (float) radius, new ArrayList<>(), null);

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
}
