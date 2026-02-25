package mod.emt.elementalcreepers.entity;

import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.entity.ai.EntityAIFriendlyCreeperSwell;
import mod.emt.elementalcreepers.init.ECLootTables;
import mod.emt.elementalcreepers.init.ECSoundEvents;
import mod.emt.elementalcreepers.misc.EntityOnlyExplosion;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ECEntityFriendlyCreeper extends EntityTameable {
    private static final DataParameter<Integer> REMAINING_ANGER_TIME = EntityDataManager.createKey(ECEntityFriendlyCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(ECEntityFriendlyCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(ECEntityFriendlyCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(ECEntityFriendlyCreeper.class, DataSerializers.BOOLEAN);

    private static final float START_HEALTH = (float) ECConfig.ENTITIES.FRIENDLY_CREEPER.maxHealth;
    private static final float TAME_HEALTH = (float) ECConfig.ENTITIES.FRIENDLY_CREEPER.tamedMaxHealth;
    private static final int ANGER_MIN = 400;
    private static final int ANGER_MAX = 780;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    public int cooldown;
    private double explosionRadius = ECConfig.ENTITIES.FRIENDLY_CREEPER.explosionRadius;
    private UUID persistentAngerTarget;

    public ECEntityFriendlyCreeper(World world) {
        super(world);
        this.setSize(0.6F, 1.7F);
        this.setTamed(false);
    }

    @Override
    protected void initEntityAI() {
        if (this.aiSit == null) {
            this.aiSit = new EntityAISit(this);
        }

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFriendlyCreeperSwell(this));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 10, true, false, this::isAngryAt));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ECConfig.ENTITIES.FRIENDLY_CREEPER.armor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(START_HEALTH);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ECConfig.ENTITIES.FRIENDLY_CREEPER.movementSpeed);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(REMAINING_ANGER_TIME, 0);
        this.dataManager.register(STATE, -1);
        this.dataManager.register(POWERED, false);
        this.dataManager.register(IGNITED, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger("Anger", this.dataManager.get(REMAINING_ANGER_TIME));
        if (this.persistentAngerTarget != null) {
            compound.setUniqueId("AngryAt", this.persistentAngerTarget);
        }

        if (this.dataManager.get(POWERED)) {
            compound.setBoolean("powered", true);
        }

        compound.setShort("Fuse", (short) this.fuseTime);
        compound.setByte("ExplosionRadius", (byte) this.explosionRadius);
        compound.setBoolean("ignited", this.isIgnited());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(POWERED, compound.getBoolean("powered"));

        this.dataManager.set(REMAINING_ANGER_TIME, compound.getInteger("Anger"));
        if (compound.hasUniqueId("AngryAt")) {
            this.persistentAngerTarget = compound.getUniqueId("AngryAt");
        }

        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
    }

    public boolean getPowered() {
        return this.dataManager.get(POWERED);
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.cooldown > 0) {
                this.cooldown--;
            } else {
                if (this.isIgnited()) {
                    this.setCreeperState(1);
                }

                int i = this.getCreeperState();

                if (i > 0 && this.timeSinceIgnited == 0) {
                    this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
                }

                this.timeSinceIgnited += i;

                if (this.timeSinceIgnited < 0) {
                    this.timeSinceIgnited = 0;
                }

                if (this.timeSinceIgnited >= this.fuseTime) {
                    this.timeSinceIgnited = 0;
                    this.cooldown = 80;
                    this.setCreeperState(-1);
                    this.explode();
                }
            }
        }

        super.onUpdate();
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        int bonus = (this.getAttackTarget() == null) ? 0 : (int) (this.getHealth() - 1.0F);
        super.fall(Math.max(0, distance - bonus), damageMultiplier);

        this.timeSinceIgnited += (int) (distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    public boolean isPowered() {
        return this.dataManager.get(POWERED);
    }

    public float getCreeperFlashIntensity(float partialTicks) {
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * partialTicks) / (float) (this.fuseTime - 2);
    }

    public int getCreeperState() {
        return this.dataManager.get(STATE);
    }

    public void setCreeperState(int state) {
        this.dataManager.set(STATE, state);
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(POWERED, true);
    }

    private void explode() {
        if (!this.world.isRemote) {
            double radius = this.explosionRadius;

            if (this.isPowered()) {
                radius *= 1.5D;
            }

            Map<EntityPlayer, Vec3d> hitPlayers = EntityOnlyExplosion.explodeAt(
                    this.world, this, this.posX, this.posY, this.posZ,
                    (float) radius, 0.8D, 1.0D
            );

            this.handleNetworkedExplosionEffects(radius, hitPlayers, ECSoundEvents.RANDOM_EXPLOSION_CLASSIC.getSoundEvent());
        }
    }

    protected void handleNetworkedExplosionEffects(double radius, @Nullable Map<EntityPlayer, Vec3d> hitPlayers, SoundEvent soundEvent) {
        double x = this.posX;
        double y = this.posY;
        double z = this.posZ;

        if (!this.world.isRemote) {
            this.world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 4.0F,
                    (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
        }

        if (!this.world.isRemote && this.world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) this.world;

            for (EntityPlayerMP player : worldServer.getPlayers(EntityPlayerMP.class, p -> true)) {
                if (player.getDistanceSq(x, y, z) < 4096.0D) {
                    Vec3d motion = (hitPlayers != null && hitPlayers.containsKey(player)) ? hitPlayers.get(player) : Vec3d.ZERO;
                    player.connection.sendPacket(new SPacketExplosion(x, y, z, (float) radius, Collections.emptyList(), motion));
                }
            }
        }

        this.spawnLingeringCloud();
    }

    private void spawnLingeringCloud() {
        Collection<PotionEffect> collection = this.getActivePotionEffects();

        if (!collection.isEmpty()) {
            EntityAreaEffectCloud areaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            areaeffectcloud.setRadius(2.5F);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());

            for (PotionEffect potioneffect : collection) {
                areaeffectcloud.addEffect(new PotionEffect(potioneffect));
            }

            this.world.spawnEntity(areaeffectcloud);
        }
    }

    public boolean isIgnited() {
        return this.dataManager.get(IGNITED);
    }

    public void ignite() {
        this.dataManager.set(IGNITED, true);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            int angerTime = this.dataManager.get(REMAINING_ANGER_TIME);

            if (angerTime > 0) {
                this.dataManager.set(REMAINING_ANGER_TIME, angerTime - 1);
                if (angerTime - 1 <= 0) {
                    this.persistentAngerTarget = null;
                }
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();

            if (this.aiSit != null) {
                this.aiSit.setSitting(false);
            }

            if (entity instanceof EntityLivingBase) {
                this.setPersistentAngerTarget(entity.getUniqueID());
                this.startPersistentAngerTimer();
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);

        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(TAME_HEALTH);
            this.setHealth(TAME_HEALTH);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(START_HEALTH);

            if (this.getHealth() > START_HEALTH) {
                this.setHealth(START_HEALTH);
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (this.world.isRemote) {
            return this.isTamed() && this.isOwner(player) || stack.getItem() == Items.GUNPOWDER;
        }

        if (this.isTamed()) {
            if (stack.getItem() == Items.GUNPOWDER && this.getHealth() < this.getMaxHealth()) {
                this.heal(10.0F);
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                return true;
            }

            if (this.isOwner(player)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
                return true;
            }
        } else if (stack.getItem() == Items.GUNPOWDER && !this.isAngry()) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                this.setTamedBy(player);
                this.navigator.clearPath();
                this.aiSit.setSitting(true);
                this.world.setEntityState(this, (byte) 7);
            } else {
                this.world.setEntityState(this, (byte) 6);
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

    public int getRemainingPersistentAngerTime() {
        return this.dataManager.get(REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int time) {
        this.dataManager.set(REMAINING_ANGER_TIME, time);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_MIN + this.rand.nextInt(ANGER_MAX - ANGER_MIN));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    public boolean isAngryAt(EntityLivingBase entity) {
        if (entity == null || this.persistentAngerTarget == null) {
            return false;
        }

        return entity.getUniqueID().equals(this.persistentAngerTarget) && this.isAngry();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.GUNPOWDER;
    }

    public boolean isAngry() {
        return this.getRemainingPersistentAngerTime() > 0;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        ECEntityFriendlyCreeper baby = new ECEntityFriendlyCreeper(this.world);

        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            baby.setOwnerId(uuid);
            baby.setTamed(true);
        }

        return baby;
    }

    @Override
    public boolean canMateWith(EntityAnimal entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(entity instanceof ECEntityFriendlyCreeper)) {
            return false;
        } else {
            ECEntityFriendlyCreeper other = (ECEntityFriendlyCreeper) entity;
            if (!other.isTamed()) {
                return false;
            } else if (other.isSitting()) {
                return false;
            } else {
                return this.isInLove() && other.isInLove();
            }
        }
    }

    @Override
    public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
        if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
            if (target instanceof EntityTameable) {
                EntityTameable tamable = (EntityTameable) target;
                return !tamable.isTamed() || tamable.getOwner() != owner;
            } else if (target instanceof EntityPlayer && owner instanceof EntityPlayer
                    && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target)) {
                return false;
            } else if (target instanceof AbstractHorse && ((AbstractHorse) target).isTame()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return !this.isAngry() && super.canBeLeashedTo(player);
    }

    protected boolean isValidLightLevel() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
            return false;
        } else {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering()) {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        IBlockState state = this.world.getBlockState((new BlockPos(this)).down());

        return ECConfig.ENTITIES.FRIENDLY_CREEPER.surfaceSpawning ?
                this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.canSeeSky(new BlockPos(this)) && this.isValidLightLevel()
                        && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F && state.canEntitySpawn(this) :
                this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel()
                        && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F && state.canEntitySpawn(this);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ECLootTables.FRIENDLY_CREEPER;
    }
}
