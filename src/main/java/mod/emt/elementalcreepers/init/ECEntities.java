package mod.emt.elementalcreepers.init;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import mod.emt.elementalcreepers.client.render.*;
import mod.emt.elementalcreepers.config.ECConfig;
import mod.emt.elementalcreepers.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ElementalCreepersRedux.MOD_ID)
public class ECEntities {
    private static int entityID = 1;

    public static void registerEntity(String name, Class<? extends Entity> clazz, int eggColor1, int eggColor2) {
        EntityRegistry.registerModEntity(new ResourceLocation(ElementalCreepersRedux.MOD_ID, name), clazz, ElementalCreepersRedux.MOD_ID + "." + name, entityID++, ElementalCreepersRedux.instance, 64, 1, true, eggColor1, eggColor2);
    }

    public static void registerEntity(String name, Class<? extends Entity> clazz) {
        EntityRegistry.registerModEntity(new ResourceLocation(ElementalCreepersRedux.MOD_ID, name), clazz, ElementalCreepersRedux.MOD_ID + "." + name, entityID++, ElementalCreepersRedux.instance, 64, 1, true);
    }

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event) {
        ElementalCreepersRedux.LOGGER.info("Registering entities...");

        if (ECConfig.ENTITIES.COOKIE_CREEPER.enableEntity) registerEntity("cookie_creeper", ECEntityCookieCreeper.class, 13011540, 8342837);
        if (ECConfig.ENTITIES.DARK_CREEPER.enableEntity) registerEntity("dark_creeper", ECEntityDarkCreeper.class, 3421236, Color.WHITE.getRGB());
        if (ECConfig.ENTITIES.EARTH_CREEPER.enableEntity) registerEntity("earth_creeper", ECEntityEarthCreeper.class, 7224073, 7396445);
        if (ECConfig.ENTITIES.ELECTRIC_CREEPER.enableEntity) registerEntity("electric_creeper", ECEntityElectricCreeper.class, 16772666, Color.BLACK.getRGB());
        if (ECConfig.ENTITIES.FIRE_CREEPER.enableEntity) registerEntity("fire_creeper", ECEntityFireCreeper.class, 16740369, Color.BLACK.getRGB());
        if (ECConfig.ENTITIES.FRIENDLY_CREEPER.enableEntity) registerEntity("friendly_creeper", ECEntityFriendlyCreeper.class, 14183121, Color.BLACK.getRGB());
        if (ECConfig.ENTITIES.GHOST_CREEPER.enableEntity) registerEntity("ghost_creeper", ECEntityGhostCreeper.class, 7644532, 1118481);
        if (ECConfig.ENTITIES.ILLUSION_CREEPER.enableEntity) registerEntity("illusion_creeper", ECEntityIllusionCreeper.class, 12303291, 7434609);
        if (ECConfig.ENTITIES.LIGHT_CREEPER.enableEntity) registerEntity("light_creeper", ECEntityLightCreeper.class, 16774535, 9800765);
        if (ECConfig.ENTITIES.MAGMA_CREEPER.enableEntity) registerEntity("magma_creeper", ECEntityMagmaCreeper.class, 12006990, Color.BLACK.getRGB());
        if (ECConfig.ENTITIES.PSYCHIC_CREEPER.enableEntity) registerEntity("psychic_creeper", ECEntityPsychicCreeper.class, 10180028, Color.BLACK.getRGB());
        if (ECConfig.ENTITIES.REVERSE_CREEPER.enableEntity) registerEntity("reverse_creeper", ECEntityReverseCreeper.class, Color.BLACK.getRGB(), 894731);
        if (ECConfig.ENTITIES.SPIDER_CREEPER.enableEntity) registerEntity("spider_creeper", ECEntitySpiderCreeper.class, 2493707, 11013646);
        if (ECConfig.ENTITIES.WATER_CREEPER.enableEntity) registerEntity("water_creeper", ECEntityWaterCreeper.class, 5603516, Color.BLACK.getRGB());
        if (ECConfig.ENTITIES.WINTER_CREEPER.enableEntity) registerEntity("winter_creeper", ECEntityWinterCreeper.class, 13816530, Color.BLACK.getRGB());

        registerEntitySpawns();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(@Nonnull final ModelRegistryEvent event) {
        ElementalCreepersRedux.LOGGER.info("Registering entity renderers...");

        if (ECConfig.ENTITIES.COOKIE_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityCookieCreeper.class, new ECRenderCookieCreeper.Factory());
        if (ECConfig.ENTITIES.DARK_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityDarkCreeper.class, new ECRenderDarkCreeper.Factory());
        if (ECConfig.ENTITIES.EARTH_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityEarthCreeper.class, new ECRenderEarthCreeper.Factory());
        if (ECConfig.ENTITIES.ELECTRIC_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityElectricCreeper.class, new ECRenderElectricCreeper.Factory());
        if (ECConfig.ENTITIES.FIRE_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityFireCreeper.class, new ECRenderFireCreeper.Factory());
        if (ECConfig.ENTITIES.FRIENDLY_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityFriendlyCreeper.class, new ECRenderFriendlyCreeper.Factory());
        if (ECConfig.ENTITIES.GHOST_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityGhostCreeper.class, new ECRenderGhostCreeper.Factory());
        if (ECConfig.ENTITIES.ILLUSION_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityIllusionCreeper.class, new ECRenderIllusionCreeper.Factory());
        if (ECConfig.ENTITIES.LIGHT_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityLightCreeper.class, new ECRenderLightCreeper.Factory());
        if (ECConfig.ENTITIES.MAGMA_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityMagmaCreeper.class, new ECRenderMagmaCreeper.Factory());
        if (ECConfig.ENTITIES.PSYCHIC_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityPsychicCreeper.class, new ECRenderPsychicCreeper.Factory());
        if (ECConfig.ENTITIES.REVERSE_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityReverseCreeper.class, new ECRenderReverseCreeper.Factory());
        if (ECConfig.ENTITIES.SPIDER_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntitySpiderCreeper.class, new ECRenderSpiderCreeper.Factory());
        if (ECConfig.ENTITIES.WATER_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityWaterCreeper.class, new ECRenderWaterCreeper.Factory());
        if (ECConfig.ENTITIES.WINTER_CREEPER.enableEntity) RenderingRegistry.registerEntityRenderingHandler(ECEntityWinterCreeper.class, new ECRenderWinterCreeper.Factory());
    }

    public static void registerEntitySpawns() {
        if (ECConfig.ENTITIES.COOKIE_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.COOKIE_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityCookieCreeper.class, ECConfig.ENTITIES.COOKIE_CREEPER.spawnWeight, ECConfig.ENTITIES.COOKIE_CREEPER.spawnMin, ECConfig.ENTITIES.COOKIE_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityCookieCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.DARK_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.DARK_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityDarkCreeper.class, ECConfig.ENTITIES.DARK_CREEPER.spawnWeight, ECConfig.ENTITIES.DARK_CREEPER.spawnMin, ECConfig.ENTITIES.DARK_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityDarkCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.EARTH_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.EARTH_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityEarthCreeper.class, ECConfig.ENTITIES.EARTH_CREEPER.spawnWeight, ECConfig.ENTITIES.EARTH_CREEPER.spawnMin, ECConfig.ENTITIES.EARTH_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityEarthCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.ELECTRIC_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.ELECTRIC_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityElectricCreeper.class, ECConfig.ENTITIES.ELECTRIC_CREEPER.spawnWeight, ECConfig.ENTITIES.ELECTRIC_CREEPER.spawnMin, ECConfig.ENTITIES.ELECTRIC_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityElectricCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.FIRE_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.FIRE_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityFireCreeper.class, ECConfig.ENTITIES.FIRE_CREEPER.spawnWeight, ECConfig.ENTITIES.FIRE_CREEPER.spawnMin, ECConfig.ENTITIES.FIRE_CREEPER.spawnMax, EnumCreatureType.MONSTER, ECConfig.ENTITIES.FIRE_CREEPER.classicSpawning ? getEntityBiomes(EntityCreeper.class) : getBiomeTypes(Type.NETHER));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityFireCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.FRIENDLY_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.FRIENDLY_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityFriendlyCreeper.class, ECConfig.ENTITIES.FRIENDLY_CREEPER.spawnWeight, ECConfig.ENTITIES.FRIENDLY_CREEPER.spawnMin, ECConfig.ENTITIES.FRIENDLY_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityFriendlyCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.GHOST_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.GHOST_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityGhostCreeper.class, ECConfig.ENTITIES.GHOST_CREEPER.spawnWeight, ECConfig.ENTITIES.GHOST_CREEPER.spawnMin, ECConfig.ENTITIES.GHOST_CREEPER.spawnMax, EnumCreatureType.MONSTER, ECConfig.ENTITIES.GHOST_CREEPER.classicSpawning ? getEntityBiomes(EntityCreeper.class) : getBiomeTypes(Type.SPOOKY));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityGhostCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.ILLUSION_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.ILLUSION_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityIllusionCreeper.class, ECConfig.ENTITIES.ILLUSION_CREEPER.spawnWeight, ECConfig.ENTITIES.ILLUSION_CREEPER.spawnMin, ECConfig.ENTITIES.ILLUSION_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityIllusionCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.LIGHT_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.LIGHT_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityLightCreeper.class, ECConfig.ENTITIES.LIGHT_CREEPER.spawnWeight, ECConfig.ENTITIES.LIGHT_CREEPER.spawnMin, ECConfig.ENTITIES.LIGHT_CREEPER.spawnMax, EnumCreatureType.MONSTER, ECConfig.ENTITIES.LIGHT_CREEPER.classicSpawning ? getEntityBiomes(EntityCreeper.class) : getBiomeTypes(Type.NETHER));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityLightCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.MAGMA_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.MAGMA_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityMagmaCreeper.class, ECConfig.ENTITIES.MAGMA_CREEPER.spawnWeight, ECConfig.ENTITIES.MAGMA_CREEPER.spawnMin, ECConfig.ENTITIES.MAGMA_CREEPER.spawnMax, EnumCreatureType.MONSTER, getBiomeTypes(Type.NETHER));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityMagmaCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.PSYCHIC_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.PSYCHIC_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityPsychicCreeper.class, ECConfig.ENTITIES.PSYCHIC_CREEPER.spawnWeight, ECConfig.ENTITIES.PSYCHIC_CREEPER.spawnMin, ECConfig.ENTITIES.PSYCHIC_CREEPER.spawnMax, EnumCreatureType.MONSTER, ECConfig.ENTITIES.PSYCHIC_CREEPER.classicSpawning ? getEntityBiomes(EntityCreeper.class) : getBiomeTypes(Type.HILLS, Type.MOUNTAIN));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityPsychicCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.REVERSE_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.REVERSE_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityReverseCreeper.class, ECConfig.ENTITIES.REVERSE_CREEPER.spawnWeight, ECConfig.ENTITIES.REVERSE_CREEPER.spawnMin, ECConfig.ENTITIES.REVERSE_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            if (ECConfig.ENTITIES.REVERSE_CREEPER.spawnWeightEnd > 0) {
                EntityRegistry.addSpawn(ECEntityReverseCreeper.class, ECConfig.ENTITIES.REVERSE_CREEPER.spawnWeightEnd, ECConfig.ENTITIES.REVERSE_CREEPER.spawnMin, ECConfig.ENTITIES.REVERSE_CREEPER.spawnMax, EnumCreatureType.MONSTER, getBiomeTypes(Type.END));
            }
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityReverseCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.SPIDER_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.SPIDER_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntitySpiderCreeper.class, ECConfig.ENTITIES.SPIDER_CREEPER.spawnWeight, ECConfig.ENTITIES.SPIDER_CREEPER.spawnMin, ECConfig.ENTITIES.SPIDER_CREEPER.spawnMax, EnumCreatureType.MONSTER, getEntityBiomes(EntityCreeper.class));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntitySpiderCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.WATER_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.WATER_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityWaterCreeper.class, ECConfig.ENTITIES.WATER_CREEPER.spawnWeight, ECConfig.ENTITIES.WATER_CREEPER.spawnMin, ECConfig.ENTITIES.WATER_CREEPER.spawnMax, EnumCreatureType.MONSTER, ECConfig.ENTITIES.WATER_CREEPER.classicSpawning ? getEntityBiomes(EntityCreeper.class) : getBiomeTypes(Type.BEACH, Type.OCEAN, Type.RIVER));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityWaterCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }

        if (ECConfig.ENTITIES.WINTER_CREEPER.spawnWeight > 0 && ECConfig.ENTITIES.WINTER_CREEPER.enableEntity) {
            EntityRegistry.addSpawn(ECEntityWinterCreeper.class, ECConfig.ENTITIES.WINTER_CREEPER.spawnWeight, ECConfig.ENTITIES.WINTER_CREEPER.spawnMin, ECConfig.ENTITIES.WINTER_CREEPER.spawnMax, EnumCreatureType.MONSTER, ECConfig.ENTITIES.WINTER_CREEPER.classicSpawning ? getEntityBiomes(EntityCreeper.class) : getBiomeTypes(Type.SNOWY));
            EntitySpawnPlacementRegistry.setPlacementType(ECEntityWinterCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        }
    }

    // Gets biomes from selected entity.
    public static Biome[] getEntityBiomes(Class<? extends Entity> spawn) {
        List<Biome> biomes = new ArrayList<>();

        for (Biome biome : ForgeRegistries.BIOMES) {
            List<Biome.SpawnListEntry> spawnList = biome.getSpawnableList(EnumCreatureType.MONSTER);

            for (Biome.SpawnListEntry list : spawnList) {
                if (list.entityClass == spawn) {
                    biomes.add(biome);
                    break;
                }
            }
        }

        return biomes.toArray(new Biome[0]);
    }

    // Get all biome types.
    public static Biome[] getBiomeTypes(BiomeDictionary.Type... types) {
        Set<Biome> biomes = new HashSet<>();

        for (BiomeDictionary.Type type : types) {
            biomes.addAll(BiomeDictionary.getBiomes(type));
        }
        return biomes.toArray(new Biome[0]);
    }
}
