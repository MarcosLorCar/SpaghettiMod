package me.orange.spaghettimod.block.entity;

import me.orange.spaghettimod.Spaghettimod;
import me.orange.spaghettimod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public  static BlockEntityType<CookingPotEntity> COOKING_POT;
    public static void registerBlockEntities() {
        COOKING_POT = Registry.register(Registries.BLOCK_ENTITY_TYPE,new Identifier("cooking_pot", Spaghettimod.MOD_ID), FabricBlockEntityTypeBuilder.create(CookingPotEntity::new, ModBlocks.COOKING_POT).build(null));
    }
}
