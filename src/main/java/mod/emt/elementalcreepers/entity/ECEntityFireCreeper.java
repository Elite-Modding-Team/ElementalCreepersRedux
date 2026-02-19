package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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

        handleNetworkedExplosionEffects(radius, SoundEvents.ITEM_FIRECHARGE_USE);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.FIRE_CREEPER;
    }
}
