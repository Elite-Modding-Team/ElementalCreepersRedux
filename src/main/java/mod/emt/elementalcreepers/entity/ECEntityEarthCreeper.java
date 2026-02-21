package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ECEntityEarthCreeper extends ECEntityElementalCreeper {
    public ECEntityEarthCreeper(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.EARTH_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.EARTH_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.EARTH_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.EARTH_CREEPER.explosionRadius;

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

    @Override
    public boolean getCanSpawnHere() {
        return ECConfig.ENTITIES.EARTH_CREEPER.undergroundSpawning ? super.getCanSpawnHere() && !this.world.canSeeSky(new BlockPos(this)) && (this.posY <= ECConfig.ENTITIES.EARTH_CREEPER.undergroundSpawningMaxYHeight)
                : super.getCanSpawnHere();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.EARTH_CREEPER;
    }
}
