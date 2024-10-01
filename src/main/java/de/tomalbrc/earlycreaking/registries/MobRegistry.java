package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.Creaking;
import de.tomalbrc.earlycreaking.Util;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import eu.pb4.polymer.core.api.item.PolymerSpawnEggItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobRegistry {
    public static final EntityType<Creaking> PENGUIN = register(
            Creaking.ID,
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(Creaking::new)
                    .spawnGroup(MobCategory.CREATURE)
                    .dimensions(EntityDimensions.scalable(0.6f, 0.95f))
                    .defaultAttributes(Creaking::createAttributes)
                    .spawnRestriction(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules)
    );

    private static <T extends Entity> EntityType<T> register(ResourceLocation id, FabricEntityTypeBuilder<T> builder) {
        EntityType<T> type = builder.build();
        PolymerEntityUtils.registerType(type);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, type);
    }

    public static void registerMobs() {
        addSpawnEgg(PENGUIN, Items.SNOW_GOLEM_SPAWN_EGG);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void addSpawnEgg(EntityType type, Item item) {
        Item spawnEgg = new PolymerSpawnEggItem(type, item, new Item.Properties());
        ItemRegistry.register(Util.id(EntityType.getKey(type).getPath() + "_spawn_egg"), spawnEgg);
    }
}
