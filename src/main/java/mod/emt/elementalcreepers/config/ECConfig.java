package mod.emt.elementalcreepers.config;

import mod.emt.elementalcreepers.ElementalCreepersRedux;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ElementalCreepersRedux.MOD_ID, name = "ElementalCreepersRedux")
public class ECConfig {
    @Config.LangKey("cfg.elementalcreepers.general")
    @Config.Name("General")
    public static final GeneralSettings GENERAL = new GeneralSettings();

    @Config.LangKey("config.elementalcreepers.entities")
    @Config.Comment("Settings for entities")
    public static final Entities ENTITIES = new Entities();

    public static class Entities {
        @Config.LangKey("config.elementalcreepers.cookie_creeper")
        @Config.Comment("Cookie Creeper settings")
        public final CookieCreeper COOKIE_CREEPER = new CookieCreeper();

        @Config.LangKey("config.elementalcreepers.dark_creeper")
        @Config.Comment("Dark Creeper settings")
        public final DarkCreeper DARK_CREEPER = new DarkCreeper();

        @Config.LangKey("config.elementalcreepers.earth_creeper")
        @Config.Comment("Earth Creeper settings")
        public final EarthCreeper EARTH_CREEPER = new EarthCreeper();

        @Config.LangKey("config.elementalcreepers.electric_creeper")
        @Config.Comment("Electric Creeper settings")
        public final ElectricCreeper ELECTRIC_CREEPER = new ElectricCreeper();

        @Config.LangKey("config.elementalcreepers.fire_creeper")
        @Config.Comment("Fire Creeper settings")
        public final FireCreeper FIRE_CREEPER = new FireCreeper();

        @Config.LangKey("config.elementalcreepers.friendly_creeper")
        @Config.Comment("Friendly Creeper settings")
        public final FriendlyCreeper FRIENDLY_CREEPER = new FriendlyCreeper();

        @Config.LangKey("config.elementalcreepers.ghost_creeper")
        @Config.Comment("Ghost Creeper settings")
        public final GhostCreeper GHOST_CREEPER = new GhostCreeper();

        @Config.LangKey("config.elementalcreepers.illusion_creeper")
        @Config.Comment("Illusion Creeper settings")
        public final IllusionCreeper ILLUSION_CREEPER = new IllusionCreeper();

        @Config.LangKey("config.elementalcreepers.light_creeper")
        @Config.Comment("Light Creeper settings")
        public final LightCreeper LIGHT_CREEPER = new LightCreeper();

        @Config.LangKey("config.elementalcreepers.magma_creeper")
        @Config.Comment("Magma Creeper settings")
        public final MagmaCreeper MAGMA_CREEPER = new MagmaCreeper();

        @Config.LangKey("config.elementalcreepers.psychic_creeper")
        @Config.Comment("Psychic Creeper settings")
        public final PsychicCreeper PSYCHIC_CREEPER = new PsychicCreeper();

        @Config.LangKey("config.elementalcreepers.reverse_creeper")
        @Config.Comment("Reverse Creeper settings")
        public final ReverseCreeper REVERSE_CREEPER = new ReverseCreeper();

        @Config.LangKey("config.elementalcreepers.spider_creeper")
        @Config.Comment("Spider Creeper settings")
        public final SpiderCreeper SPIDER_CREEPER = new SpiderCreeper();

        @Config.LangKey("config.elementalcreepers.water_creeper")
        @Config.Comment("Water Creeper settings")
        public final WaterCreeper WATER_CREEPER = new WaterCreeper();

        @Config.LangKey("config.elementalcreepers.winter_creeper")
        @Config.Comment("Winter (Ice) Creeper settings")
        public final WinterCreeper WINTER_CREEPER = new WinterCreeper();

        public static class CookieCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Cookie Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Cookie Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Cookie Quantity")
            @Config.Comment("The maximum number of cookies dropped by the Cookie Creeper")
            @Config.RangeInt(min = 0, max = 256)
            public int cookieQuantity = 8;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Cookie Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Cookie Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Cookie Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 1;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Cookie Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Cookie Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 2;
        }

        public static class DarkCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Dark Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Dark Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Dark Creeper")
            public double explosionRadius = 12.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Dark Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Dark Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Dark Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 4;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Dark Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Dark Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 20;

            @Config.Name("Underground Spawning")
            @Config.Comment("Makes the Dark Creeper only spawn underground and prevents it from spawning under the sky")
            public boolean undergroundSpawning = true;

            @Config.Name("Underground Spawning Max Y Height")
            @Config.Comment("The maximum Y height that the Dark Creeper can spawn up to")
            public double undergroundSpawningMaxYHeight = 50.0;
        }

        public static class EarthCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Earth Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Earth Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Earth Creeper")
            public double explosionRadius = 8.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Earth Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Earth Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Earth Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 2;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Earth Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Earth Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 20;

            @Config.Name("Underground Spawning")
            @Config.Comment("Makes the Earth Creeper only spawn underground and prevents it from spawning under the sky")
            public boolean undergroundSpawning = true;

            @Config.Name("Underground Spawning Max Y Height")
            @Config.Comment("The maximum Y height that the Earth Creeper can spawn up to")
            public double undergroundSpawningMaxYHeight = 50.0;
        }

        public static class ElectricCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Electric Creeper has")
            public double armor = 0;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Electric Creeper")
            public double explosionRadius = 8.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Electric Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Electric Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Surface Spawning")
            @Config.Comment("Makes the Electric Creeper only spawn on the surface and prevents it from spawning in caves")
            public boolean surfaceSpawning = true;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Electric Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 2;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Electric Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Electric Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 10;
        }

        public static class FireCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Fire Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Fire Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Classic Spawning")
            @Config.Comment("Makes the Fire Creeper spawn everywhere (excluding dimension and mushroom biome types) regardless of biome, just like the original Elemental Creepers versions")
            public boolean classicSpawning = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Fire Creeper")
            public double explosionRadius = 6.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Fire Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Fire Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Fire Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 4;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Fire Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 4;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Fire Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 40;
        }

        public static class FriendlyCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Friendly Creeper has")
            public double armor = 0;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Friendly Creeper")
            public double explosionRadius = 3.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Friendly Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Friendly Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Surface Spawning")
            @Config.Comment("Makes the Friendly Creeper only spawn on the surface and prevents it from spawning in caves")
            public boolean surfaceSpawning = true;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Friendly Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 1;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Friendly Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Friendly Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 10;
        }

        public static class GhostCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Ghost Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Ghost Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Classic Spawning")
            @Config.Comment("Makes the Ghost Creeper spawn everywhere (excluding dimension and mushroom biome types)) regardless of biome, just like the original Elemental Creepers versions")
            public boolean classicSpawning = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Ghost Creeper")
            public double explosionRadius = 3.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Ghost Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Ghost Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Ghost Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 4;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Ghost Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 4;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Ghost Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 50;
        }

        public static class IllusionCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Illusion Creeper has")
            public double armor = 0;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Illusion Creeper")
            public double explosionRadius = 3.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Illusion Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Illusion Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Illusion Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 2;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Illusion Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Illusion Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 20;
        }

        public static class LightCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Light Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Light Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Classic Spawning")
            @Config.Comment("Makes the Light Creeper spawn everywhere (excluding dimension and mushroom biome types) regardless of biome, just like the original Elemental Creepers versions")
            public boolean classicSpawning = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Light Creeper")
            public double explosionRadius = 3.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Light Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Light Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Light Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 1;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Light Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Light Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 10;
        }

        public static class MagmaCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Magma Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Magma Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Magma Creeper")
            public double explosionRadius = 5.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Magma Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Magma Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Magma Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 1;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Magma Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Magma Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 20;
        }

        public static class PsychicCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Psychic Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Psychic Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Classic Spawning")
            @Config.Comment("Makes the Psychic Creeper spawn everywhere (excluding dimension and mushroom biome types) regardless of biome, just like the original Elemental Creepers versions")
            public boolean classicSpawning = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Psychic Creeper")
            public double explosionRadius = 3.0;

            @Config.Name("Explosion Knockback Strength")
            @Config.Comment("The explosion knockback strength of the Psychic Creeper")
            public double explosionKnockbackStrength = 5.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Psychic Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Psychic Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Surface Spawning")
            @Config.Comment("Makes the Psychic Creeper only spawn on the surface and prevents it from spawning in caves")
            public boolean surfaceSpawning = true;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Psychic Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 1;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Psychic Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Psychic Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 20;
        }

        public static class ReverseCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Reverse Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Reverse Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("End Spawning")
            @Config.Comment("Makes the Reverse Creeper also spawn in the End")
            public boolean endSpawning = true;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Reverse Creeper")
            public double explosionRadius = 6.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Reverse Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Middle Island End Spawning")
            @Config.Comment("Reverse Creepers spawning in the End will also spawn in the middle island where the dragon is located")
            public boolean middleIslandEndSpawning = false;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Reverse Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Reverse Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 4;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Reverse Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Reverse Creeper in the End")
            @Config.RangeInt(min = 0)
            public int spawnWeightEnd = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Reverse Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 5;
        }

        public static class SpiderCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Spider Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Spider Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Spider Creeper")
            public double explosionRadius = 7.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Spider Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Spider Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Poison Time")
            @Config.Comment("The time (in seconds) of the Poison effect inflicted by the Spider Creeper (PEACEFUL = 0x, EASY = 0.66x, HARD = 1.5x)")
            @Config.RangeInt(min = 0)
            public int poisonTime = 10;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Spider Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 4;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Spider Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Spider Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 20;

            @Config.Name("Underground Spawning")
            @Config.Comment("Makes the Spider Creeper only spawn underground and prevents it from spawning under the sky")
            public boolean undergroundSpawning = true;

            @Config.Name("Underground Spawning Max Y Height")
            @Config.Comment("The maximum Y height that the Spider Creeper can spawn up to")
            public double undergroundSpawningMaxYHeight = 50.0;
        }

        public static class WaterCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Water Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Water Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Classic Spawning")
            @Config.Comment("Makes the Water Creeper spawn everywhere (excluding dimension and mushroom biome types) regardless of biome, just like the original Elemental Creepers versions")
            public boolean classicSpawning = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Water Creeper")
            public double explosionRadius = 5.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Water Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Water Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Permanent Water Radius")
            @Config.Comment("This affects how much permanent water you want the Water Creeper to leave out after exploding")
            public double permanentWaterRadius = 1.0;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Water Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 2;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Water Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 1;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Water Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 40;
        }

        public static class WinterCreeper {
            @Config.Name("Armor")
            @Config.Comment("The amount of armor the Winter Creeper has")
            public double armor = 0;

            @Config.Name("Classic Explosion")
            @Config.Comment("Makes the Winter Creeper play the classic explosion sound from Beta after exploding")
            public boolean classicExplosionSound = false;

            @Config.Name("Classic Spawning")
            @Config.Comment("Makes the Winter Creeper spawn everywhere (excluding dimension and mushroom biome types) regardless of biome, just like the original Elemental Creepers versions")
            public boolean classicSpawning = false;

            @Config.Name("Explosion Radius")
            @Config.Comment("The explosion radius of the Winter Creeper")
            public double explosionRadius = 10.0;

            @Config.Name("Max Health")
            @Config.Comment("The maximum health of the Winter Creeper")
            public double maxHealth = 20.0;

            @Config.Name("Movement Speed")
            @Config.Comment("The movement speed of the Winter Creeper")
            public double movementSpeed = 0.25;

            @Config.Name("Spawn Max")
            @Config.Comment("The maximum spawn group size of the Winter Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMax = 4;

            @Config.Name("Spawn Min")
            @Config.Comment("The minimum spawn group size of the Winter Creeper")
            @Config.RangeInt(min = 0)
            public int spawnMin = 4;

            @Config.Name("Spawn Weight")
            @Config.Comment("The spawn weight of the Winter Creeper")
            @Config.RangeInt(min = 0)
            public int spawnWeight = 50;
        }

    }

    public static class GeneralSettings {
    }

    @Mod.EventBusSubscriber(modid = ElementalCreepersRedux.MOD_ID)
    public static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ElementalCreepersRedux.MOD_ID)) {
                ConfigManager.sync(ElementalCreepersRedux.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
