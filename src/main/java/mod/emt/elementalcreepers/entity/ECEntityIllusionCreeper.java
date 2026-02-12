package mod.emt.elementalcreepers.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ECEntityIllusionCreeper extends ECEntityElementalCreeper {
    private static final DataParameter<Boolean> IS_FAKE = EntityDataManager.createKey(ECEntityIllusionCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_SPLIT = EntityDataManager.createKey(ECEntityIllusionCreeper.class, DataSerializers.BOOLEAN);

    public ECEntityIllusionCreeper(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_FAKE, Boolean.FALSE);
        this.dataManager.register(IS_SPLIT, Boolean.FALSE);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("IsFake", this.isFake());
        compound.setBoolean("IsSplit", this.isSplit());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setFake(compound.getBoolean("IsFake"));
        this.setSplit(compound.getBoolean("IsSplit"));
    }

    public void setFake(boolean fake) {
        this.dataManager.set(IS_FAKE, fake);
    }

    public boolean isFake() {
        return this.dataManager.get(IS_FAKE);
    }

    public void setSplit(boolean split) {
        this.dataManager.set(IS_SPLIT, split);
    }

    public boolean isSplit() {
        return this.dataManager.get(IS_SPLIT);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote && !this.isSplit() && !this.isFake()) {
            EntityPlayer player = this.world.getNearestAttackablePlayer(this, 8.0D, 8.0D);

            if (player != null) {
                for (int i = 0; i < 4; ++i) {
                    ECEntityIllusionCreeper fake = new ECEntityIllusionCreeper(this.world);
                    fake.copyLocationAndAnglesFrom(this);
                    fake.setFake(true); // Mark as fake!

                    fake.motionX = (this.rand.nextDouble() * 0.5D) - 0.25D;
                    fake.motionY = 0.5D;
                    fake.motionZ = (this.rand.nextDouble() * 0.5D) - 0.25D;

                    this.world.spawnEntity(fake);
                }

                this.motionX = (this.rand.nextDouble() * 0.5D) - 0.25D;
                this.motionY = 0.5D;
                this.motionZ = (this.rand.nextDouble() * 0.5D) - 0.25D;

                this.spawnExplosionParticle();
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
                this.setSplit(true);
            }
        }
    }

    @Override
    public void creeperEffect() {
        // Disappear instead of exploding when fake
        if (this.isFake()) {
            this.spawnExplosionParticle();
            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
            return;
        }

        float f = this.getPowered() ? 1.5F : 1.0F;
        boolean mobGriefing = this.world.getGameRules().getBoolean("mobGriefing");
        this.world.createExplosion(this, this.posX, this.posY, this.posZ, 3.0F * f, mobGriefing);

        this.spawnLingeringCloud();
    }

    @Override
    protected ResourceLocation getLootTable() {
        return this.isFake() ? null : super.getLootTable();
    }
}
