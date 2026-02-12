package mod.emt.elementalcreepers.misc;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

// Like a regular explosion except it doesn't destroy blocks
public class EntityOnlyExplosion {
    public static Map<EntityPlayer, Vec3d> explodeAt(World world, Entity source, double x, double y, double z, double radius, double damageMulti, double launchMulti) {
        double diameter = radius * 2.0D;
        int k1 = MathHelper.floor(x - diameter - 1.0D);
        int l1 = MathHelper.floor(x + diameter + 1.0D);
        int i2 = MathHelper.floor(y - diameter - 1.0D);
        int i1 = MathHelper.floor(y + diameter + 1.0D);
        int j2 = MathHelper.floor(z - diameter - 1.0D);
        int j1 = MathHelper.floor(z + diameter + 1.0D);

        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(source, new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));

        Vec3d centerVec = new Vec3d(x, y, z);
        Map<EntityPlayer, Vec3d> hitPlayers = Maps.newHashMap();

        for (Entity entity : list) {
            if (source instanceof EntityTameable) {
                EntityTameable tamable = (EntityTameable) source;

                if (tamable.isTamed()) {
                    // Don't harm our owner or players which cannot be harmed by our owner.
                    EntityLivingBase owner = tamable.getOwner();

                    if (owner == entity) continue;

                    if (owner instanceof EntityPlayer && entity instanceof EntityPlayer) {
                        if (!((EntityPlayer) owner).canAttackPlayer((EntityPlayer) entity)) {
                            continue;
                        }
                    }
                }
            }

            if (!entity.isImmuneToExplosions()) {
                double distanceRatio = entity.getDistance(x, y, z) / diameter;

                if (distanceRatio <= 1.0D) {
                    double dX = entity.posX - x;
                    double dY = entity.posY + (double) entity.getEyeHeight() - y;
                    double dZ = entity.posZ - z;
                    double dist = (double) MathHelper.sqrt(dX * dX + dY * dY + dZ * dZ);

                    if (dist != 0.0D) {
                        dX /= dist;
                        dY /= dist;
                        dZ /= dist;

                        double density = (double) world.getBlockDensity(centerVec, entity.getEntityBoundingBox());
                        double exposure = (1.0D - distanceRatio) * density;

                        if (damageMulti > 0) {
                            entity.attackEntityFrom(DamageSource.causeExplosionDamage(
                                            source instanceof EntityLivingBase ? (EntityLivingBase) source : null),
                                    (float) ((int) (damageMulti * ((exposure * exposure + exposure) / 2.0D * 7.0D * diameter + 1.0D))));
                        }

                        double knockbackResist = 1.0D;
                        if (entity instanceof EntityLivingBase) {
                            knockbackResist = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, exposure);
                        }

                        dX *= knockbackResist * launchMulti;
                        dY *= knockbackResist * launchMulti;
                        dZ *= knockbackResist * launchMulti;

                        if (launchMulti > 1.0D) {
                            dY = Math.min(0.5D, dY);
                        }

                        entity.motionX += dX;
                        entity.motionY += dY;
                        entity.motionZ += dZ;

                        if (entity instanceof EntityPlayer) {
                            EntityPlayer player = (EntityPlayer) entity;

                            if (!player.isSpectator() && (!player.isCreative() || !player.capabilities.isFlying)) {
                                hitPlayers.put(player, new Vec3d(dX, dY, dZ));
                            }
                        }
                    }
                }
            }
        }

        return hitPlayers;
    }
}
