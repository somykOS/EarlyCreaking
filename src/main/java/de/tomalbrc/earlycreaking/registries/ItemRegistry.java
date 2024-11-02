package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.TexturedPolymerItem;
import de.tomalbrc.earlycreaking.Util;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ItemRegistry {
    public static final Object2ObjectLinkedOpenHashMap<Identifier, Item> CUSTOM_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static Item RESIN_CLUMP = register(Identifier.of("minecraft","resin_clump"), new TexturedPolymerItem(Identifier.of("minecraft","item/resin_clump")));
    public static Item RESIN_BRICK = register(Identifier.of("minecraft","resin_brick"), new TexturedPolymerItem(Identifier.of("minecraft","item/resin_brick")));
    public static Item WOODEN_HEART = register(Util.id("wooden_heart"), new TexturedPolymerItem(Util.id("item/wooden_heart")));

    public static void registerItems() {
        ItemGroup ITEM_GROUP = new ItemGroup.Builder(null, -1)
                .displayName(Text.literal("Early Creaking").formatted(Formatting.DARK_PURPLE))
                .icon(() -> Registries.ITEM.get(Identifier.of("minecraft", "creaking_heart")).getDefaultStack())
                .entries((parameters, output) -> CUSTOM_ITEMS.forEach((key, value) -> output.add(value)))
                .build();

        PolymerItemGroupUtils.registerPolymerItemGroup(Util.id("items"), ITEM_GROUP);
    }

    public static <T extends Item> T register(Identifier identifier, T item) {
        CUSTOM_ITEMS.putIfAbsent(identifier, item);
        return Registry.register(Registries.ITEM, identifier, item);
    }
}
