package mod.emt.elementalcreepers.config;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ECConfigLists {
    public static List<Block> lightBlocks = new ArrayList<>();

    public static void initLists() {
        lightBlocks.clear();

        try {
            for (String entry : ECConfig.ENTITIES.DARK_CREEPER.lightBlockList) {
                ResourceLocation resLoc = new ResourceLocation(entry);

                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) {
                    lightBlocks.add(ForgeRegistries.BLOCKS.getValue(resLoc));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
