package mod.emt.elementalcreepers.client.model;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

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
            this.head.setRotationPoint(0.0F, entityTamed.isChild() ? 8.0F : 12.0F, 0.0F);
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
            this.head.setRotationPoint(0.0F, entityTamed.isChild() ? 4.0F : 6.0F, 0.0F);
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

    @Override
    public void render(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        if (this.isChild) {
            float headScale = 2.0F;
            float bodyScale = 2.0F;

            GlStateManager.pushMatrix();
            GlStateManager.scale(1.5F / headScale, 1.5F / headScale, 1.5F / headScale);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F / bodyScale, 1.0F / bodyScale, 1.0F / bodyScale);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.body.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.head.render(scale);
            this.body.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
        }
    }
}
