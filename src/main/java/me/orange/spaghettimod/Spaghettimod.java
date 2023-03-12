package me.orange.spaghettimod;

import me.orange.spaghettimod.block.ModBlocks;
import me.orange.spaghettimod.block.entity.ModBlockEntities;
import me.orange.spaghettimod.item.ModItemGroups;
import me.orange.spaghettimod.item.ModItems;
import me.orange.spaghettimod.villager.ModVillagers;
import me.orange.spaghettimod.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;

import java.util.EventListener;

public class Spaghettimod implements ModInitializer {

    public static final String MOD_ID = "spaghettimod";

    @Override
    public void onInitialize() {


        ModItemGroups.registerModGroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerBlockEntities();
        ModVillagers.registerTrades();
        ModOreGeneration.generateOres();

    }
}
