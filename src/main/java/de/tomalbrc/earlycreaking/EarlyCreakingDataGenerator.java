package de.tomalbrc.earlycreaking;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import de.tomalbrc.earlycreaking.datagen.*;

public class EarlyCreakingDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

//		pack.addProvider(ModRecipeProvider::new);
//		pack.addProvider(ModModelProvider::new);
//		pack.addProvider(ModBlockLootTables::new);
	}
}
