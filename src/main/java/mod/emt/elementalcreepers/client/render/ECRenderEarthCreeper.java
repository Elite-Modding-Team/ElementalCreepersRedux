package mod.emt.elementalcreepers.client.render;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.entity.ECEntityEarthCreeper;
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
public class ECRenderEarthCreeper extends RenderCreeper {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ElementalCreepersRedux.MOD_ID, "textures/entity/earth_creeper.png");

    public ECRenderEarthCreeper(RenderManager render) {
        super(render);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<ECEntityEarthCreeper> {
        @Override
        public Render<? super ECEntityEarthCreeper> createRenderFor(RenderManager manager) {
            return new ECRenderEarthCreeper(manager);
        }
    }
}
