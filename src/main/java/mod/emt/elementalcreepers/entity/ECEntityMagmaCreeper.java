package mod.emt.elementalcreepers.entity;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ECEntityMagmaCreeper extends ECEntityElementalCreeper {
    public ECEntityMagmaCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    public void creeperEffect() {
        IBlockState flowingState = Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, 4);
        double radius = 5.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = (int) -radius - 1; y <= radius; y++) {
                for (int z = (int) -radius - 1; z <= radius; z++) {

                    BlockPos blockPos = new BlockPos((int) this.posX + x, (int) this.posY + y, (int) this.posZ + z);

                    double distSqr = (x * x) + (y * y) + (z * z);

                    if (this.world.isAirBlock(blockPos) && distSqr <= rSqr) {
                        this.world.setBlockState(blockPos, flowingState, 2);
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
    }
}
