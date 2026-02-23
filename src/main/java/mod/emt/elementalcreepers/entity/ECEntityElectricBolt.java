package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;

public class ECEntityElectricBolt extends EntityLightningBolt {
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;

    public ECEntityElectricBolt(World world) {
        this(world, 0.0D, 0.0D, 0.0D);
    }

    public ECEntityElectricBolt(World world, double x, double y, double z) {
        super(world, x, y, z, true);
        this.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
    }

    @Override
    public void onUpdate() {
        this.onEntityUpdate();

        if (this.lightningState == 2) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, ECSoundEvents.RANDOM_ELECTRIC_STRIKE.getSoundEvent(), SoundCategory.WEATHER, 10000.0F, 0.5F + this.rand.nextFloat() * 0.2F);
            this.world.playSound(null, this.posX, this.posY, this.posZ, ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent(), SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
        }

        --this.lightningState;

        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
            } else if (this.lightningState < -this.rand.nextInt(10)) {
                --this.boltLivingTime;
                this.lightningState = 1;

                if (!this.world.isRemote) {
                    this.boltVertex = this.rand.nextLong();
                }
            }
        }

        if (this.lightningState >= 0) {
            if (this.world.isRemote) {
                this.world.setLastLightningBolt(2);
            } else {
                AxisAlignedBB aabb = new AxisAlignedBB(this.posX - 3.0D, this.posY - 3.0D, this.posZ - 3.0D, this.posX + 3.0D, this.posY + 6.0D + 3.0D, this.posZ + 3.0D);
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, aabb);

                for (Entity entity : list) {
                    if (!ForgeEventFactory.onEntityStruckByLightning(entity, this)) {
                        if (entity instanceof EntityCreeper) {
                            EntityCreeper creeper = (EntityCreeper) entity;

                            // Don't harm creepers after they're charged
                            if (!creeper.getPowered()) {
                                entity.onStruckByLightning(this);
                            }
                        } else {
                            entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, (float) ECConfig.ENTITIES.ELECTRIC_CREEPER.lightningBoltDamage);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }
}
