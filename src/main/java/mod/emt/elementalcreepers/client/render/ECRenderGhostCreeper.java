package mod.emt.elementalcreepers.client.render;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.entity.ECEntityGhostCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ECRenderGhostCreeper extends RenderCreeper {
    //private static final ResourceLocation TEXTURE = new ResourceLocation(ElementalCreepersRedux.MOD_ID, "textures/entity/ghost_creeper.png");

    public ECRenderGhostCreeper(RenderManager render) {
        super(render);
    }

    @Override
    protected void preRenderCallback(EntityCreeper entity, float partialTickTime) {
        super.preRenderCallback(entity, partialTickTime);

        // Spawn animation and pulse effect
        float spawnTime = (entity.ticksExisted + partialTickTime) / 15.0F;
        float fadeTime = MathHelper.clamp(MathHelper.sqrt(spawnTime), 0.0F, 1.0F);
        float pulseTime = (entity.ticksExisted + partialTickTime) * 0.05F;
        float pulse = (MathHelper.sin(pulseTime) + 1.0F) / 2.0F;
        float alpha = (0.2F + (pulse * 0.4F)) * fadeTime;
        float r = 0.6F + (pulse * 0.3F);
        float g = 0.7F + (pulse * 0.25F);
        float b = 0.8F + (pulse * 0.2F);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        if (fadeTime < 1.0F) {
            float glow = (1.0F - fadeTime) * 0.5F;
            GlStateManager.color(r + glow, g + glow, b + glow, alpha);
        } else {
            GlStateManager.color(r, g, b, alpha);
        }

        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.enableLighting();
    }

    @Override
    protected float getDeathMaxRotation(EntityCreeper entity) {
        return 0.0F;
    }

    /*@Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return TEXTURE;
    }*/

    public static class Factory implements IRenderFactory<ECEntityGhostCreeper> {
        @Override
        public Render<? super ECEntityGhostCreeper> createRenderFor(RenderManager manager) {
            return new ECRenderGhostCreeper(manager);
        }
    }
}
