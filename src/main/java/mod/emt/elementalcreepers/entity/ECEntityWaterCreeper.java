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

public class ECEntityWaterCreeper extends ECEntityElementalCreeper {
    public ECEntityWaterCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.WATER_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.WATER_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.WATER_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.WATER_CREEPER.explosionRadius;
        double fullRadius = ECConfig.ENTITIES.WATER_CREEPER.permanentWaterRadius;

        if (this.getPowered()) {
            radius *= 1.5;
            fullRadius *= 1.5;
        }

        double rSqr = radius * radius;
        double fullSqr = fullRadius * fullRadius;

        for (int x = (int) -radius - 1; x <= radius; x++) {
            for (int y = (int) -radius - 1; y <= radius; y++) {
                // Only place one permanent block of water per x/y coord, so it's a puddle on the ground.
                boolean placedPermanentAtThisXY = false;

                for (int z = (int) -radius - 1; z <= radius; z++) {
                    BlockPos blockPos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);
                    double distSqr = (double) x * x + (double) y * y + (double) z * z;

                    if (distSqr <= rSqr && this.world.isAirBlock(blockPos)) {
                        IBlockState stateToPlace;

                        if (distSqr <= fullSqr && !placedPermanentAtThisXY) {
                            stateToPlace = Blocks.WATER.getDefaultState();
                            placedPermanentAtThisXY = true;
                        } else {
                            stateToPlace = Blocks.FLOWING_WATER.getDefaultState().withProperty(BlockLiquid.LEVEL, 4);
                        }

                        this.world.setBlockState(blockPos, stateToPlace, 2);
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.ENTITY_HOSTILE_SPLASH);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.WATER_CREEPER;
    }
}
