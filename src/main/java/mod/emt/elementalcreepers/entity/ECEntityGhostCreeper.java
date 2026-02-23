package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import mod.emt.elementalcreepers.misc.EntityOnlyExplosion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public class ECEntityGhostCreeper extends ECEntityElementalCreeper {
    public ECEntityGhostCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.GHOST_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.GHOST_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.GHOST_CREEPER.movementSpeed);
    }

    @Override
    public void creeperEffect() {
        double radius = ECConfig.ENTITIES.GHOST_CREEPER.explosionRadius;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        EntityOnlyExplosion.explodeAt(this.world, this, this.posX, this.posY, this.posZ,
                radius, 3.0, 1.0D
        );

        if (!ECConfig.ENTITIES.GHOST_CREEPER.classicExplosionSound) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, ECSoundEvents.RANDOM_GHOST_EXPLOSION.getSoundEvent(), SoundCategory.HOSTILE, 2.0F, 0.8F + (world.rand.nextFloat() * 0.4F));
        }

        handleNetworkedExplosionEffects(radius, ECConfig.ENTITIES.GHOST_CREEPER.classicExplosionSound ? ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent() : SoundEvents.ENTITY_GENERIC_EXPLODE);
    }

    // Slightly more special death animation
    @Override
    protected void onDeathUpdate() {
        ++this.deathTime;

        if (this.deathTime == 2) {
            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))) {
                int i = this.getExperiencePoints(this.attackingPlayer);
                i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);

                while (i > 0) {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }

            world.playSound(null, this.posX, this.posY, this.posZ, ECSoundEvents.RANDOM_GHOST_VANISH.getSoundEvent(), SoundCategory.AMBIENT, 1.0F, 0.8F + (world.rand.nextFloat() * 0.4F));
            this.setDead();
            this.spawnExplosionParticle();
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.GHOST_CREEPER;
    }
}
