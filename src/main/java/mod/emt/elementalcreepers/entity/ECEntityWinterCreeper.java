package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

// Formerly Ice Creeper
public class ECEntityWinterCreeper extends ECEntityElementalCreeper {
    public ECEntityWinterCreeper(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.WINTER_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.WINTER_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.WINTER_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.WINTER_CREEPER.explosionRadius;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = (int) -radius - 1; y <= radius; y++) {
                for (int z = (int) -radius - 1; z <= radius; z++) {

                    double distSqr = (x * x) + (y * y) + (z * z);

                    if (distSqr <= rSqr) {
                        BlockPos blockPos = new BlockPos((int) this.posX + x, (int) this.posY + y, (int) this.posZ + z);
                        IBlockState blockState = this.world.getBlockState(blockPos);
                        Block block = blockState.getBlock();

                        // Freeze water and lava
                        if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
                            this.world.setBlockState(blockPos, Blocks.ICE.getDefaultState(), 3);
                            continue;
                        } else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
                            if (blockState.getValue(BlockLiquid.LEVEL) == 0) {
                                this.world.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState(), 3);
                            } else {
                                this.world.setBlockState(blockPos, Blocks.COBBLESTONE.getDefaultState(), 3);
                            }
                            continue;
                        }

                        BlockPos belowPos = blockPos.down(); // Same as offset(0, -1, 0)

                        if (this.world.isAirBlock(blockPos)
                                && this.world.getBlockState(belowPos).isSideSolid(this.world, belowPos, EnumFacing.UP)
                                && this.rand.nextFloat() < 0.8) {

                            // Try to place snow on top of a solid block
                            this.world.setBlockState(blockPos, Blocks.SNOW_LAYER.getDefaultState(), 3);
                            IBlockState snowState = this.world.getBlockState(blockPos);

                            if (snowState.getBlock() == Blocks.SNOW_LAYER) {
                                // Placed successfully, grow closer to explosion center with a little variance.
                                int snowSize = Math.min(8, (int) (8 * (radius - Math.sqrt(distSqr) - 1 + 2 * this.rand.nextDouble()) / radius));

                                if (snowSize > 1) {
                                    IBlockState grownState = snowState.withProperty(BlockSnow.LAYERS, snowSize);
                                    this.world.setBlockState(blockPos, grownState, 3);
                                }
                            }
                        }
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.BLOCK_SNOW_PLACE);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.WINTER_CREEPER;
    }
}
