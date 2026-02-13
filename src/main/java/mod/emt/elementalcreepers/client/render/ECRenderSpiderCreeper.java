package mod.emt.elementalcreepers.client.render;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.client.model.ECModelSpiderCreeper;
import mod.emt.elementalcreepers.entity.ECEntitySpiderCreeper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ECRenderSpiderCreeper extends RenderCreeper {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ElementalCreepersRedux.MOD_ID, "textures/entity/spider_creeper.png");

    public ECRenderSpiderCreeper(RenderManager render) {
        super(render);
        this.mainModel = new ECModelSpiderCreeper();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<ECEntitySpiderCreeper> {
        @Override
        public Render<? super ECEntitySpiderCreeper> createRenderFor(RenderManager manager) {
            return new ECRenderSpiderCreeper(manager);
        }
    }
}
