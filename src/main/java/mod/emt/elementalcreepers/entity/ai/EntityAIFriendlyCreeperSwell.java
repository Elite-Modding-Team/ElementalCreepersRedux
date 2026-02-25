package mod.emt.elementalcreepers.entity.ai;

import mod.emt.elementalcreepers.entity.ECEntityFriendlyCreeper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFriendlyCreeperSwell extends EntityAIBase {
    ECEntityFriendlyCreeper swellingCreeper;
    EntityLivingBase creeperAttackTarget;

    public EntityAIFriendlyCreeperSwell(ECEntityFriendlyCreeper entity) {
        this.swellingCreeper = entity;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || (target != null && this.swellingCreeper.getDistanceSq(target) < 9.0D);
    }

    @Override
    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPath();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }

    @Override
    public void resetTask() {
        this.creeperAttackTarget = null;
    }

    @Override
    public void updateTask() {
        if (this.swellingCreeper.cooldown > 0) {
            this.swellingCreeper.setCreeperState(-1);
        } else if (this.creeperAttackTarget == null) {
            this.swellingCreeper.setCreeperState(-1);
        } else if (this.swellingCreeper.getDistanceSq(this.creeperAttackTarget) > 49.0D) {
            this.swellingCreeper.setCreeperState(-1);
        } else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
            this.swellingCreeper.setCreeperState(-1);
        } else {
            this.swellingCreeper.setCreeperState(1);
        }
    }
}
