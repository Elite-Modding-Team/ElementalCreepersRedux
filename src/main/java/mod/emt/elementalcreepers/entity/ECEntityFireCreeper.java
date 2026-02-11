package mod.emt.elementalcreepers.entity;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
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

public class ECEntityFireCreeper extends ECEntityElementalCreeper {
    public ECEntityFireCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    public void creeperEffect() {
        double radius = 6.0;
        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = (int) -radius - 1; y <= radius; y++) {
                for (int z = (int) -radius - 1; z <= radius; z++) {

                    double distSqr = (double) x * x + (double) y * y + (double) z * z;

                    if (distSqr <= rSqr) {
                        BlockPos blockPos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);
                        BlockPos belowPos = blockPos.down();

                        if (this.world.isAirBlock(blockPos)
                                && this.world.getBlockState(belowPos).isSideSolid(this.world, belowPos, EnumFacing.UP)
                                && this.rand.nextBoolean()) {

                            this.world.setBlockState(blockPos, Blocks.FIRE.getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.BLOCK_FIRE_AMBIENT);
    }
}
