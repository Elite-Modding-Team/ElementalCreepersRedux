package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ECEntityReverseCreeper extends ECEntityElementalCreeper {
    public ECEntityReverseCreeper(World world) {
        super(world);
    }

    @Override
    public void creeperEffect() {
        double radius = 6.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = 1; y <= radius; y++) {
                for (int z = (int) -radius - 1; z <= radius; z++) {

                    double distSqr = (x * x) + (y * y) + (z * z);

                    if (distSqr <= rSqr) {
                        BlockPos posA = new BlockPos((int) this.posX + x, (int) this.posY + y, (int) this.posZ + z);
                        BlockPos posB = new BlockPos((int) this.posX + x, (int) this.posY - (y - 1), (int) this.posZ + z);

                        // Prevent flipping blocks out of build height.
                        if (posA.getY() < 0 || posA.getY() > 255 || posB.getY() < 0 || posB.getY() > 255) {
                            continue;
                        }

                        IBlockState stateA = this.world.getBlockState(posA);
                        IBlockState stateB = this.world.getBlockState(posB);

                        // Prevent flipping blocks which can't be destroyed/pushed.
                        if (stateA.getBlockHardness(this.world, posA) == -1.0F
                                || stateB.getBlockHardness(this.world, posB) == -1.0F) {
                            continue;
                        }

                        // Prevent flipping entities which we can't preserve properly.
                        if (this.world.getTileEntity(posA) != null || this.world.getTileEntity(posB) != null) {
                            continue;
                        }

                        this.world.setBlockState(posA, stateB, 3);
                        this.world.setBlockState(posB, stateA, 3);
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.BLOCK_PISTON_EXTEND);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.REVERSE_CREEPER;
    }
}
