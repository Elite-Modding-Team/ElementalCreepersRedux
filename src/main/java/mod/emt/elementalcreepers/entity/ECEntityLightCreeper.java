package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ECEntityLightCreeper extends ECEntityElementalCreeper {
    public ECEntityLightCreeper(World world) {
        super(world);
    }

    @Override
    public void creeperEffect() {
        double radius = 4.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = (int) -radius - 1; y <= radius; y++) {
                for (int z = (int) -radius - 1; z <= radius; z++) {

                    double distSqr = (x * x) + (y * y) + (z * z);

                    BlockPos blockPos = new BlockPos((int) this.posX + x, (int) this.posY + y, (int) this.posZ + z);

                    if (this.world.isAirBlock(blockPos) && distSqr <= rSqr && this.rand.nextFloat() < 0.75f) {
                        this.world.setBlockState(blockPos, Blocks.GLOWSTONE.getDefaultState(), 3);
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.BLOCK_STONE_PLACE);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.LIGHT_CREEPER;
    }
}
