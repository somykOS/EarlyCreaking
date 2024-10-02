package de.tomalbrc.earlycreaking;

import de.tomalbrc.earlycreaking.registries.BlockRegistry;
import de.tomalbrc.earlycreaking.registries.ItemRegistry;
import de.tomalbrc.earlycreaking.registries.MobRegistry;
import de.tomalbrc.earlycreaking.registries.SoundRegistry;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

public class EarlyCreaking implements ModInitializer {
    public static final String MOD_ID = "earlycreaking";

    @Override
    public void onInitialize() {
        ItemRegistry.registerItems();
        SoundRegistry.registerSounds();
        MobRegistry.registerMobs();
        BlockRegistry.registerBlocks();

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();
    }
}
