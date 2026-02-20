package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.init.ECLootTables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ECEntityCookieCreeper extends ECEntityElementalCreeper {
    public ECEntityCookieCreeper(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.COOKIE_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ECConfig.ENTITIES.COOKIE_CREEPER.maxHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.COOKIE_CREEPER.movementSpeed);
    }

    @Override
    protected void creeperEffect() {
        int amount = ECConfig.ENTITIES.COOKIE_CREEPER.cookieQuantity;

        if (this.getPowered()) {
            amount = (int) (amount * 1.5);
        }

        for (int i = 0; i < amount; ++i) {
            double dx = this.rand.nextDouble() - 0.5D;
            double dy = this.rand.nextDouble() + 0.5D;
            double dz = this.rand.nextDouble() - 0.5D;

            EntityItem cookieEntity = new EntityItem(this.world, this.posX + dx, this.posY + dy, this.posZ + dz,
                    new ItemStack(Items.COOKIE, 1));

            cookieEntity.setDefaultPickupDelay();

            if (!this.world.isRemote) {
                this.world.spawnEntity(cookieEntity);
            }
        }

        handleNetworkedExplosionEffects(5.0D, SoundEvents.ENTITY_GENERIC_EAT);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.COOKIE_CREEPER;
    }
}
