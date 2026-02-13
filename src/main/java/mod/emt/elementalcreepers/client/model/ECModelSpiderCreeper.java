package mod.emt.elementalcreepers.client.model;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ECModelSpiderCreeper extends ModelCreeper {
    public ModelRenderer leftSideLeg;
    public ModelRenderer rightSideLeg;

    public ECModelSpiderCreeper() {
        this(0.0F);
    }

    public ECModelSpiderCreeper(float scale) {
        super(scale);

        this.rightSideLeg = new ModelRenderer(this, 0, 16);
        this.rightSideLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale);
        this.rightSideLeg.setRotationPoint(-6.0F, 18.0F, 0.0F);

        this.leftSideLeg = new ModelRenderer(this, 0, 16);
        this.leftSideLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, scale);
        this.leftSideLeg.setRotationPoint(6.0F, 18.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        this.leftSideLeg.render(scale);
        this.rightSideLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        this.leftSideLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 2.0F * (float) Math.PI / 3.0F) * 1.4F * limbSwingAmount;
        this.rightSideLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 4.0F * (float) Math.PI / 3.0F) * 1.4F * limbSwingAmount;
    }
}
