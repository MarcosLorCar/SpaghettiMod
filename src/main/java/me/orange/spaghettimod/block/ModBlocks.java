package me.orange.spaghettimod.block;

import me.orange.spaghettimod.Spaghettimod;
import me.orange.spaghettimod.block.custom.CookingPotBlock;
import me.orange.spaghettimod.block.custom.SpaghettiOreBlock;
import me.orange.spaghettimod.block.custom.TomatoCropBlock;
import me.orange.spaghettimod.item.ModItemGroups;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block SPAGHETTI_ORE = registerBlock("spaghetti_ore", new SpaghettiOreBlock(), new FabricItemSettings(), ModItemGroups.SPAGHETTI_TAB);
    public static final Block SPAGHETTI_BLOCK = registerBlock("spaghetti_block", new Block(FabricBlockSettings.of(Material.FROGLIGHT).strength(1)), new FabricItemSettings(), ModItemGroups.SPAGHETTI_TAB);

    public static final Block TOMATO_CROP = registerBlockNoItem("tomato_crop", new TomatoCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));

    public static final Block COOKING_POT = registerBlock("cooking_pot", new CookingPotBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)), new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD), ModItemGroups.SPAGHETTI_TAB);

    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Spaghettimod.MOD_ID,name), block);
    }

    private static Block registerBlock(String name, Block block, Item.Settings settings, ItemGroup tab) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Spaghettimod.MOD_ID, name), new BlockItem(block, settings));
        ItemGroupEvents.modifyEntriesEvent(tab).register(entries -> entries.add(item));
        return Registry.register(Registries.BLOCK, new Identifier(Spaghettimod.MOD_ID, name), block);
    }

    public static void registerModBlocks() {
        CookingPotBlock.registerDefaultCookaleItems();
    }
}
