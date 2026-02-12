package mod.emt.elementalcreepers.client.render;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.entity.ECEntityElectricCreeper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ECRenderElectricCreeper extends RenderCreeper {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ElementalCreepersRedux.MOD_ID, "textures/entity/electric_creeper.png");

    public ECRenderElectricCreeper(RenderManager render) {
        super(render);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<ECEntityElectricCreeper> {
        @Override
        public Render<? super ECEntityElectricCreeper> createRenderFor(RenderManager manager) {
            return new ECRenderElectricCreeper(manager);
        }
    }
}
