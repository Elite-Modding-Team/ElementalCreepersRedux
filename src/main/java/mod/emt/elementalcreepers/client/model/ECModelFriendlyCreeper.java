package mod.emt.elementalcreepers.client.model;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ECModelFriendlyCreeper extends ModelCreeper {
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        boolean isSitting = false;
        EntityTameable entityTamed = (EntityTameable) entity;
        if (entityTamed instanceof EntityTameable) {
            isSitting = entityTamed.isSitting();
        }

        if (isSitting) {
            this.head.setRotationPoint(0.0F, entityTamed.isChild() ? 6.0F : 12.0F, 0.0F);
            this.body.setRotationPoint(0.0F, 12.0F, 0.0F);
            this.leg2.setRotationPoint(2.0F, 22.0F, 2.0F);
            this.leg1.setRotationPoint(-2.0F, 22.0F, 2.0F);
            this.leg4.setRotationPoint(2.0F, 22.0F, -2.0F);
            this.leg3.setRotationPoint(-2.0F, 22.0F, -2.0F);

            this.leg2.rotateAngleX = (float) Math.PI / 2.0F;
            this.leg1.rotateAngleX = (float) Math.PI / 2.0F;
            this.leg4.rotateAngleX = 3.0F * (float) Math.PI / 2.0F;
            this.leg3.rotateAngleX = 3.0F * (float) Math.PI / 2.0F;

            this.leg2.rotateAngleY = (float) Math.PI / 18.0F;
            this.leg1.rotateAngleY = (float) -Math.PI / 18.0F;
            this.leg4.rotateAngleY = (float) -Math.PI / 18.0F;
            this.leg3.rotateAngleY = (float) Math.PI / 18.0F;
        } else {
            this.head.setRotationPoint(0.0F, entityTamed.isChild() ? 3.0F : 6.0F, 0.0F);
            this.body.setRotationPoint(0.0F, 6.0F, 0.0F);
            this.leg2.setRotationPoint(2.0F, 18.0F, 4.0F);
            this.leg1.setRotationPoint(-2.0F, 18.0F, 4.0F);
            this.leg4.setRotationPoint(2.0F, 18.0F, -4.0F);
            this.leg3.setRotationPoint(-2.0F, 18.0F, -4.0F);

            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

            this.leg1.rotateAngleY = 0.0F;
            this.leg2.rotateAngleY = 0.0F;
            this.leg3.rotateAngleY = 0.0F;
            this.leg4.rotateAngleY = 0.0F;
        }

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }
}
