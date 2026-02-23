package mod.emt.elementalcreepers.client.render.layer;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.client.render.ECRenderFriendlyCreeper;
import mod.emt.elementalcreepers.entity.ECEntityFriendlyCreeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ECLayerFriendlyCreeperCharge implements LayerRenderer<ECEntityFriendlyCreeper> {
    private static final ResourceLocation CHARGE_TEXTURE = new ResourceLocation(ElementalCreepersRedux.MOD_ID, "textures/entity/friendly_creeper_armor.png");
    private final ECRenderFriendlyCreeper renderer;
    private final ModelCreeper model = new ModelCreeper(2.0F);

    public ECLayerFriendlyCreeperCharge(ECRenderFriendlyCreeper renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(ECEntityFriendlyCreeper entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.getPowered()) {
            this.renderer.bindTexture(getEntityLayer(entity));
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            float f = (float) entity.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableBlend();
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.model.setModelAttributes(this.renderer.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    protected ResourceLocation getEntityLayer(ECEntityFriendlyCreeper entity) {
        return CHARGE_TEXTURE;
    }
}
