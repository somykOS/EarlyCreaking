package de.tomalbrc.earlycreaking.datagen;

import de.tomalbrc.earlycreaking.registries.BlockRegistry;
import de.tomalbrc.earlycreaking.registries.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static de.tomalbrc.earlycreaking.EarlyCreaking.MOD_ID;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BlockRegistry.RESIN_JAR_ITEM)
                .pattern(" C ")
                .pattern("CHC")
                .pattern(" B ")
                .input('C', ItemRegistry.RESIN_CLUMP)
                .input('H', ItemRegistry.WOODEN_HEART)
                .input('B', Items.GLASS_BOTTLE)
                .criterion(hasItem(ItemRegistry.RESIN_CLUMP), conditionsFromItem(ItemRegistry.RESIN_CLUMP))
                .offerTo(exporter, Identifier.of(MOD_ID, getRecipeName(BlockRegistry.RESIN_JAR_ITEM)));


    }
}
