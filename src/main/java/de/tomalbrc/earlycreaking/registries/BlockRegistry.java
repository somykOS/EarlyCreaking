package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.Util;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockRegistry {
    public static final Block CREAKING_HEART = registerBlock(Util.id("creaking_heart"), new Block(BlockBehaviour.Properties.of()));

    public static void registerBlocks() {

    }

    public static Block registerBlock(ResourceLocation identifier, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Properties());
        registerItem(identifier, blockItem);
        return Registry.register(BuiltInRegistries.BLOCK, identifier, block);
    }

    static public void registerItem(ResourceLocation identifier, Item item) {
        ItemRegistry.register(identifier, item);
    }
}
