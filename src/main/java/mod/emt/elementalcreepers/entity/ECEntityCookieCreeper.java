package mod.emt.elementalcreepers.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ECEntityCookieCreeper extends ECEntityElementalCreeper {
    public ECEntityCookieCreeper(World world) {
        super(world);
    }

    @Override
    protected void creeperEffect() {
        int amount = 5;

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
}
