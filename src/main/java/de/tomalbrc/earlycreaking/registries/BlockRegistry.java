package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.CreakingHeartBlock;
import de.tomalbrc.earlycreaking.CreakingHeartBlockEntity;
import de.tomalbrc.earlycreaking.TexturedPolymerBlockItem;
import de.tomalbrc.earlycreaking.Util;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class BlockRegistry {
    public static final Block CREAKING_HEART = registerBlock(
            Util.id("creaking_heart"),
            new CreakingHeartBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_BIRCH_LOG).pushReaction(PushReaction.BLOCK)),
            ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart_lit")
    );

    public static final BlockEntityType<CreakingHeartBlockEntity> CREAKING_HEART_BLOCK_ENTITY = registerBlockEntity(Util.id("creaking_heart"), BlockEntityType.Builder.of(CreakingHeartBlockEntity::new, CREAKING_HEART));

    public static void registerBlocks() {

    }

    public static Block registerBlock(ResourceLocation identifier, Block block, ResourceLocation itemModel) {
        BlockItem blockItem = new TexturedPolymerBlockItem(block, new Item.Properties(), itemModel);
        registerItem(identifier, blockItem);
        return Registry.register(BuiltInRegistries.BLOCK, identifier, block);
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(ResourceLocation identifier, BlockEntityType.Builder<T> builder) {
        var type = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, identifier, builder.build(null));
        PolymerBlockUtils.registerBlockEntity(type);
        return type;
    }

    static public void registerItem(ResourceLocation identifier, Item item) {
        ItemRegistry.register(identifier, item);
    }
}
