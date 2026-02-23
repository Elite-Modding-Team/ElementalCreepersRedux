package mod.emt.elementalcreepers.client.render.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class ECLayerAnimatedGlow<T extends EntityLiving> implements LayerRenderer<T> {
    private final RenderLiving<T> renderer;
    private final ResourceLocation texture;

    public ECLayerAnimatedGlow(RenderLiving<T> renderer, ResourceLocation texture) {
        this.renderer = renderer;
        this.texture = texture;
    }

    @Override
    public void doRenderLayer(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderer.bindTexture(texture);
        float time = ageInTicks + partialTicks;
        float speed = 0.1F;
        float minBrightness = 0.4F;
        float maxBrightness = 1.0F;
        float pulse = (MathHelper.sin(time * speed) * 0.5F + 0.5F);
        float intensity = minBrightness + (pulse * (maxBrightness - minBrightness));

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, entity.isInvisible() ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(-0.1F, -1.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.color(intensity, intensity, intensity, 1.0F);
        this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.renderer.setLightmap(entity);
        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
