package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ECEntityElectricCreeper extends ECEntityElementalCreeper {
    public ECEntityElectricCreeper(World world) {
        super(world);
    }

    @Override
    public void creeperEffect() {
        double radius = 8.0;

        if (this.getPowered()) {
            radius *= 1.5;
        }

        List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class,
                this.getEntityBoundingBox().grow(radius));

        for (EntityLivingBase entity : entities) {
            if (entity == this) {
                continue;
            }

            EntityLightningBolt bolt = new EntityLightningBolt(this.world, entity.posX, entity.posY, entity.posZ, false);

            this.world.addWeatherEffect(bolt);
            entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 15.0F);
        }


        handleNetworkedExplosionEffects(radius, ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent());
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.ELECTRIC_CREEPER;
    }
}
