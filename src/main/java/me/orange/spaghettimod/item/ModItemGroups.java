package me.orange.spaghettimod.item;

import me.orange.spaghettimod.Spaghettimod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup SPAGHETTI_TAB = FabricItemGroup.builder(new Identifier(Spaghettimod.MOD_ID,"spaghetti_tab"))
            .displayName(Text.literal("Spaghetti Mod"))
            .icon(() -> new ItemStack(Items.BOWL))
            .build();

    public static void registerModGroups() {

    }
}
