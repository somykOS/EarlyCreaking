package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.Creaking;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import eu.pb4.polymer.core.api.item.PolymerSpawnEggItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

public class MobRegistry {
    public static final EntityType<Creaking> CREAKING = register(
            Creaking.ID,
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(Creaking::new)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .dimensions(EntityDimensions.changing(0.6f, 3.f))
                    .defaultAttributes(Creaking::createAttributes)
                    .spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn)
    );

    private static <T extends Entity> EntityType<T> register(Identifier id, FabricEntityTypeBuilder<T> builder) {
        EntityType<T> type = builder.build();
        PolymerEntityUtils.registerType(type);
        return Registry.register(Registries.ENTITY_TYPE, id, type);
    }

    public static void registerMobs() {
        addSpawnEgg(CREAKING, Items.SNOW_GOLEM_SPAWN_EGG);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void addSpawnEgg(EntityType type, Item item) {
        Item spawnEgg = new PolymerSpawnEggItem(type, item, new Item.Settings());
        ItemRegistry.register(Identifier.of("minecraft",EntityType.getId(type).getPath() + "_spawn_egg"), spawnEgg);
    }
}
