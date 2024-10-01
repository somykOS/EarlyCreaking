package de.tomalbrc.earlycreaking.registries;

import de.tomalbrc.earlycreaking.Util;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ItemRegistry {

    public static final Object2ObjectLinkedOpenHashMap<ResourceLocation, Item> CUSTOM_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    public static void registerItems() {


        CreativeModeTab ITEM_GROUP = new CreativeModeTab.Builder(null, -1)
                .title(Component.literal("Early Creaking").withStyle(ChatFormatting.AQUA))
                .icon(Items.LAPIS_BLOCK::getDefaultInstance)
                .displayItems((parameters, output) -> CUSTOM_ITEMS.forEach((key, value) -> output.accept(value)))
                .build();

        PolymerItemGroupUtils.registerPolymerItemGroup(Util.id("items"), ITEM_GROUP);
    }

    static public void register(ResourceLocation identifier, Item item) {
        Registry.register(BuiltInRegistries.ITEM, identifier, item);
        CUSTOM_ITEMS.putIfAbsent(identifier, item);
    }
}
