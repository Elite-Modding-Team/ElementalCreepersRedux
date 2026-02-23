package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.config.ECConfigLists;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class ECEntityDarkCreeper extends ECEntityElementalCreeper {
    public ECEntityDarkCreeper(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.DARK_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.DARK_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.DARK_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.DARK_CREEPER.explosionRadius;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;
        World world = this.world;

        boolean isWhiteListMode = ECConfig.ENTITIES.DARK_CREEPER.lightBlockListMode == ECConfig.EnumLists.WHITELIST;
        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radius; y <= radius; y++) {
                for (int z = (int) -radius; z <= radius; z++) {

                    double distSqr = (double) x * x + (double) y * y + (double) z * z;
                    if (distSqr <= rSqr) {
                        BlockPos pos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);

                        if (world.isAirBlock(pos)) continue;

                        IBlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();

                        boolean inList = ECConfigLists.lightBlocks.contains(block);
                        if (isWhiteListMode != inList) {
                            continue;
                        }

                        int lightValue = state.getLightValue(world, pos);
                        float hardness = state.getBlockHardness(world, pos);

                        if (lightValue >= ECConfig.ENTITIES.DARK_CREEPER.lightBlockLightLevel && hardness >= 0 && hardness <= ECConfig.ENTITIES.DARK_CREEPER.lightBlockHardnessCap) {
                            if (!world.isRemote && world instanceof WorldServer) {
                                java.util.List<ItemStack> drops = block.getDrops(world, pos, state, 0);

                                for (ItemStack stack : drops) {
                                    Block.spawnAsEntity(world, pos, stack);
                                }

                                world.setBlockToAir(pos);
                            }
                        }
                    }
                }
            }
        }

        handleNetworkedExplosionEffects(radius, ECConfig.ENTITIES.DARK_CREEPER.classicExplosionSound ? ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent() : SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE);
    }

    @Override
    public boolean getCanSpawnHere() {
        return ECConfig.ENTITIES.DARK_CREEPER.undergroundSpawning ? super.getCanSpawnHere() && !this.world.canSeeSky(new BlockPos(this)) && (this.posY <= ECConfig.ENTITIES.DARK_CREEPER.undergroundSpawningMaxYHeight)
                : super.getCanSpawnHere();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.DARK_CREEPER;
    }
}
