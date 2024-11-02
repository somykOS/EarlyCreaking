package de.tomalbrc.earlycreaking.registries;

import eu.pb4.polymer.core.api.other.PolymerSoundEvent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    public static final SoundEvent CREAKING_AMBIENT = register("penguin.ambient", SoundEvents.ENTITY_TURTLE_AMBIENT_LAND);
    public static final SoundEvent CREAKING_HURT = register("penguin.hurt", SoundEvents.ENTITY_TURTLE_HURT);
    public static final SoundEvent CREAKING_DEATH = register("penguin.death", SoundEvents.ENTITY_TURTLE_DEATH);

    private static SoundEvent register(String name, SoundEvent soundEvent) {
        Identifier id = Identifier.tryParse("toms_mobs", name);
        return Registry.register(Registries.SOUND_EVENT, id, PolymerSoundEvent.of(id, soundEvent));
    }

    public static void registerSounds() {
    }
}

