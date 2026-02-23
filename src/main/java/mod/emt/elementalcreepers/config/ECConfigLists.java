package mod.emt.elementalcreepers.config;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ECConfigLists {
    public static List<EntityEntry> creepers = new ArrayList<>();
    public static List<Block> lightBlocks = new ArrayList<>();

    public static void initLists() {
        creepers.clear();
        lightBlocks.clear();

        try {
            for (String entry : ECConfig.ENTITIES.GHOST_CREEPER.deathSpawningList) {
                ResourceLocation resLoc = new ResourceLocation(entry);

                if (ForgeRegistries.ENTITIES.containsKey(resLoc)) {
                    creepers.add(ForgeRegistries.ENTITIES.getValue(resLoc));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
