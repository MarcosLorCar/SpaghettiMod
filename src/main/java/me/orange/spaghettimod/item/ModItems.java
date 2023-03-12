package me.orange.spaghettimod.item;

import me.orange.spaghettimod.Spaghettimod;
import me.orange.spaghettimod.block.ModBlocks;
import me.orange.spaghettimod.item.custom.ForkItem;
import me.orange.spaghettimod.item.custom.SpaghettiBowlItem;
import me.orange.spaghettimod.item.custom.SpaghettiBowlSaucedItem;
import me.orange.spaghettimod.item.custom.TomatoItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SPAGHETTI_BOWL = registerItem("spaghetti_bowl", new SpaghettiBowlItem(), ModItemGroups.SPAGHETTI_TAB);

    public static final Item SPAGHETTI_BOWL_SAUCED = registerItem("spaghetti_bowl_sauced", new SpaghettiBowlSaucedItem(), ModItemGroups.SPAGHETTI_TAB);

    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds", new AliasedBlockItem(ModBlocks.TOMATO_CROP,new FabricItemSettings()), ModItemGroups.SPAGHETTI_TAB);

    public static final Item TOMATO = registerItem("tomato", new Item(new FabricItemSettings().food(
            new FoodComponent.Builder().hunger(3).saturationModifier(2).build()
    )), ModItemGroups.SPAGHETTI_TAB);

    public static final Item SPAGHETTI_FORK = registerItem("spaghetti_fork", new ForkItem(new FabricItemSettings().maxCount(1).maxDamage(2400)), ModItemGroups.SPAGHETTI_TAB);

    public static final Item SPAGHETTI = registerItem("spaghetti", new Item(new FabricItemSettings()), ModItemGroups.SPAGHETTI_TAB);
    public static final Item TORTILLA = registerItem("tortilla", new Item(new FabricItemSettings().food(
            new FoodComponent.Builder().hunger(3).saturationModifier(2).build()
    )), ModItemGroups.SPAGHETTI_TAB);
    public static final Item MEATBALLS = registerItem("meatballs", new Item(new FabricItemSettings().food(
            new FoodComponent.Builder().hunger(5).saturationModifier(3).build()
    )), ModItemGroups.SPAGHETTI_TAB);
    private static Item registerItem(String name, Item item, ItemGroup tab) {
        Registry.register(Registries.ITEM, new Identifier(Spaghettimod.MOD_ID,name), item);
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModItems() {
        ModelPredicateProviderRegistry.register(ModItems.SPAGHETTI_FORK,new Identifier(Spaghettimod.MOD_ID,"forking"),(stack, world, entity, seed) -> stack.getOrCreateNbt().getBoolean("Forking") ? 1.0F : 0.0F);
    }
}
