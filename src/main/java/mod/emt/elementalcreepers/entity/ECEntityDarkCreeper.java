package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.misc.EntityOnlyExplosion;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Map;

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

        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radius; y <= radius; y++) {
                for (int z = (int) -radius; z <= radius; z++) {

                    double distSqr = (double) x * x + (double) y * y + (double) z * z;
                    if (distSqr <= rSqr) {
                        BlockPos pos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);
                        IBlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();

                        if (world.isAirBlock(pos) /*|| Config.darkCreeperBlacklist.contains(block)*/) {
                            continue;
                        }

                        int lightValue = state.getLightValue(world, pos);
                        float hardness = state.getBlockHardness(world, pos);

                        if (lightValue >= 8 && hardness >= 0 && hardness <= 0.8F) {
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

        handleNetworkedExplosionEffects(radius, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.DARK_CREEPER;
    }
}
