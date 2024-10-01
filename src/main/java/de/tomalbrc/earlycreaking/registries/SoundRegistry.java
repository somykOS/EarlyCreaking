package de.tomalbrc.earlycreaking.registries;

import eu.pb4.polymer.core.api.other.PolymerSoundEvent;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class SoundRegistry {
    public static final SoundEvent CREAKING_AMBIENT = register("penguin.ambient", SoundEvents.TURTLE_AMBIENT_LAND);
    public static final SoundEvent CREAKING_HURT = register("penguin.hurt", SoundEvents.TURTLE_HURT);
    public static final SoundEvent CREAKING_DEATH = register("penguin.death", SoundEvents.TURTLE_DEATH);

    private static SoundEvent register(String name, SoundEvent soundEvent) {
        ResourceLocation id = ResourceLocation.tryBuild("toms_mobs", name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, PolymerSoundEvent.of(id, soundEvent));
    }

    public static void registerSounds() {
    }
}

