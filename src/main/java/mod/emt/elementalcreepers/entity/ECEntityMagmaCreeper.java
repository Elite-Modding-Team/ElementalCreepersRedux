package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ECEntityMagmaCreeper extends ECEntityElementalCreeper {
    public ECEntityMagmaCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.MAGMA_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.MAGMA_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.MAGMA_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        IBlockState flowingState = Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, 4);
        double radius = ECConfig.ENTITIES.MAGMA_CREEPER.explosionRadius;

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

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.MAGMA_CREEPER;
    }
}
