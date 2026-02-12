package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.misc.EntityOnlyExplosion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Map;

public class ECEntityGhostCreeper extends ECEntityElementalCreeper {
    public ECEntityGhostCreeper(World world) {
        super(world);
    }

    @Override
    public void creeperEffect() {
        double radius = 3.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        Map<EntityPlayer, Vec3d> hitPlayers = EntityOnlyExplosion.explodeAt(this.world, this, this.posX, this.posY, this.posZ,
                radius, 3.0, 1.0D
        );

        /*if (!this.world.isRemote && this.world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) this.world;
            worldServer.spawnParticle(EnumParticleTypes.END_ROD, this.posX, this.posY, this.posZ, 30, 1.0, 1.0, 1.0, 0.05);
        }*/

        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.HOSTILE, 2.0F, (2.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
        handleNetworkedExplosionEffects(radius, SoundEvents.ENTITY_GENERIC_EXPLODE);
    }
}
