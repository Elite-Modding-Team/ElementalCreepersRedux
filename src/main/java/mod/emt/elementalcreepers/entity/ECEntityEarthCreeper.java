package mod.emt.elementalcreepers.entity;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ECEntityEarthCreeper extends ECEntityElementalCreeper {
    public ECEntityEarthCreeper(World world) {
        super(world);
    }

    @Override
    public void creeperEffect() {
        double radius = 8.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = (int) -radius - 1; y <= radius; y++) {
                for (int z = (int) -radius - 1; z <= radius; z++) {
                    double distSqr = (double)x * x + (double)y * y + (double)z * z;

                    BlockPos blockPos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);

                    if (this.world.isAirBlock(blockPos) && distSqr <= rSqr && this.rand.nextFloat() < 0.75F) {
                        this.world.setBlockState(blockPos, Blocks.DIRT.getDefaultState(), 3);
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.BLOCK_GRAVEL_STEP);
    }
}
