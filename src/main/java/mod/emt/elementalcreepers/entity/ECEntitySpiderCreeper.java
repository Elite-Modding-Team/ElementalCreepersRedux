package mod.emt.elementalcreepers.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ECEntitySpiderCreeper extends ECEntityElementalCreeper {
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(ECEntitySpiderCreeper.class, DataSerializers.BYTE);

    public ECEntitySpiderCreeper(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, (byte) 0);
    }

    @Override
    public void creeperEffect() {
        double radius = 7.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        double rSqr = radius * radius;
        World world = this.world;

        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radius; y <= radius; y++) {
                for (int z = (int) -radius; z <= radius; z++) {
                    double distSqr = (double) x * x + (double) y * y + (double) z * z;
                    BlockPos pos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);

                    if (distSqr <= rSqr && world.isAirBlock(pos) && world.rand.nextFloat() < 0.05F) {
                        world.setBlockState(pos, Blocks.WEB.getDefaultState(), 2);
                    }
                }
            }
        }

        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class,
                this.getEntityBoundingBox().grow(radius));

        double poisonTime = 10.0;
        switch (world.getDifficulty()) {
            case EASY:
                poisonTime *= 0.66D;
                break;
            case HARD:
                poisonTime *= 1.5D;
                break;
            case PEACEFUL:
                poisonTime = 0;
                break;
            default:
                break;
        }

        if (radius > 0 && poisonTime > 0) {
            for (EntityLivingBase entity : entities) {
                if (entity == this) continue;

                double dist = this.getDistance(entity);
                double strength = Math.min(0.5D, 1.0D - (dist / radius));

                if (strength > 0) {
                    entity.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (60 * poisonTime * strength), 1));
                }
            }
        }

        handleNetworkedExplosionEffects(radius, SoundEvents.BLOCK_SLIME_BREAK);
    }

    @Override
    public void setInWeb() {
        // Prevents it from getting stuck in webs
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateClimber(this, worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            // This check is important, otherwise it'll spin around like crazy
            boolean isMoving = Math.abs(this.motionX) > 0.01D || Math.abs(this.motionZ) > 0.01D;
            this.setClimbing(this.collidedHorizontally && isMoving);
        }
    }

    @Override
    public boolean isOnLadder() {
        return this.isClimbing();
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        if (effect.getPotion() == MobEffects.POISON) {
            return false;
        }

        return super.isPotionApplicable(effect);
    }

    public boolean isClimbing() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setClimbing(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.dataManager.set(CLIMBING, b0);
    }

    @NotNull
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }
}
