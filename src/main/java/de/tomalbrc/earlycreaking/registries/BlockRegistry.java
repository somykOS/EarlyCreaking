package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.*;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlockRegistry {
    public static final Block CREAKING_HEART = registerBlock(
            Identifier.of("minecraft","creaking_heart"),
            new CreakingHeartBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_BIRCH_LOG).pistonBehavior(PistonBehavior.BLOCK))
    );

    public static final BlockItem CREAKING_HEART_ITEM = registerItem(Identifier.of("minecraft","creaking_heart"), new TexturedPolymerBlockItem(CREAKING_HEART, new Item.Settings(), Identifier.of("minecraft", "block/creakingheart_lit")));

    public static final Block RESIN_JAR = registerBlock(
            Util.id("resin_jar"),
            new ResinJarBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).sounds(BlockSoundGroup.GLASS).luminance((state) -> 5).breakInstantly().noCollision(), Util.id("block/resin_jar"))
    );

    public static final BlockItem RESIN_JAR_ITEM = registerItem(Util.id("resin_jar"), new TexturedPolymerBlockItem(RESIN_JAR, new Item.Settings(), Util.id("block/resin_jar")));

    public static final BlockEntityType<CreakingHeartBlockEntity> CREAKING_HEART_BLOCK_ENTITY = registerBlockEntity(Util.id("creaking_heart"), BlockEntityType.Builder.create(CreakingHeartBlockEntity::new, CREAKING_HEART));

    public static void registerBlocks() {

    }

    public static Block registerBlock(Identifier identifier, Block block) {
        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(Identifier identifier, BlockEntityType.Builder<T> builder) {
        var type = Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, builder.build(null));
        PolymerBlockUtils.registerBlockEntity(type);
        return type;
    }

    public static BlockItem registerItem(Identifier identifier, BlockItem item){
        return ItemRegistry.register(identifier, item);
    }
}
