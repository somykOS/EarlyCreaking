package de.tomalbrc.earlycreaking;

import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import net.minecraft.util.Identifier;

public class Util {
    public static Identifier id(String path) {
        return Identifier.of(EarlyCreaking.MOD_ID, path);
    }

    public static Model loadBbModel(Identifier resourceLocation) {
        return BbModelLoader.load(resourceLocation);
    }
}
