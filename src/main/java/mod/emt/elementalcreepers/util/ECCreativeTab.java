package mod.emt.elementalcreepers.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ECCreativeTab extends CreativeTabs {
    public ECCreativeTab(int length, String name) {
        super(length, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(Blocks.LOG, 1);
    }
}
