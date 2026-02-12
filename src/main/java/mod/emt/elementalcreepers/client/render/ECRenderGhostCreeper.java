package mod.emt.elementalcreepers.client.render;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.entity.ECEntityGhostCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
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

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
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
