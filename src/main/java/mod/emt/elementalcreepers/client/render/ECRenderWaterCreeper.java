package mod.emt.elementalcreepers.client.render;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.entity.ECEntityWaterCreeper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ECRenderWaterCreeper extends RenderCreeper {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ElementalCreepersRedux.MOD_ID, "textures/entity/water_creeper.png");

    public ECRenderWaterCreeper(RenderManager render) {
        super(render);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<ECEntityWaterCreeper> {
        @Override
        public Render<? super ECEntityWaterCreeper> createRenderFor(RenderManager manager) {
            return new ECRenderWaterCreeper(manager);
        }
    }
}
