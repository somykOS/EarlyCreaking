package de.tomalbrc.earlycreaking;

import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.AjModelLoader;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import net.minecraft.resources.ResourceLocation;

public class Util {
    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(EarlyCreaking.MOD_ID, path);
    }

    public static Model loadModel(ResourceLocation resourceLocation) {
        return AjModelLoader.load(resourceLocation);
    }

    public static Model loadBbModel(ResourceLocation resourceLocation) {
        return BbModelLoader.load(resourceLocation);
    }
}
