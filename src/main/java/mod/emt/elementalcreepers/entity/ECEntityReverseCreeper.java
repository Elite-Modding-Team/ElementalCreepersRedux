package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
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
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.REVERSE_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.REVERSE_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.REVERSE_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.REVERSE_CREEPER.explosionRadius;

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

        handleNetworkedExplosionEffects(radius, ECConfig.ENTITIES.REVERSE_CREEPER.classicExplosionSound ? ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent() : SoundEvents.BLOCK_PISTON_EXTEND);
    }

    @Override
    public boolean getCanSpawnHere() {
        // Middle end island check
        if (this.world.provider.getDimension() == 1) {
            if (!ECConfig.ENTITIES.REVERSE_CREEPER.middleIslandEndSpawning) {
                return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this)) && (this.posX > 500.0D || this.posX < -500.0D || this.posZ > 500.0D || this.posZ < -500.0D);
            } else {
                return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this));
            }
        }

        return super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        // Reduce numbers in the End
        return this.world.provider.getDimension() == 1 ? 1 : 4;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.REVERSE_CREEPER;
    }
}
