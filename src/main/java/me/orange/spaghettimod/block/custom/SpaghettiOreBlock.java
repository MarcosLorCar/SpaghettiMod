package me.orange.spaghettimod.block.custom;

import me.orange.spaghettimod.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SpaghettiOreBlock extends Block {

    public static final Integer max_forking_time = 30;

    public static IntProperty FORKING_TIME = IntProperty.of("forking_time",0,max_forking_time);

    public static IntProperty FORKING_STAGE = IntProperty.of("forking_stage",0,3);
    public SpaghettiOreBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_ORE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(FORKING_TIME,FORKING_STAGE));
    }
}
