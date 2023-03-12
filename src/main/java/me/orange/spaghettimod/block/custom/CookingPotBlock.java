package me.orange.spaghettimod.block.custom;

import me.orange.spaghettimod.block.entity.CookingPotEntity;
import me.orange.spaghettimod.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class CookingPotBlock extends BlockWithEntity implements BlockEntityProvider {

    public static Map<ItemConvertible, Integer> COOKABLE_ITEMS;

    /*
    1-common
    2-rare
    3-epic
    4-legendary
    5-mythic
     */

    private static void registerCookableItem(ItemConvertible item, int quality) {
        COOKABLE_ITEMS.put(item,quality);
    }

    public static void registerDefaultCookaleItems() {
        COOKABLE_ITEMS = new HashMap<>();
        registerCookableItem(ModItems.TOMATO,3);
        registerCookableItem(Items.APPLE,1);
        registerCookableItem(Items.BREAD,1);
        registerCookableItem(Items.POTATO,1);
        registerCookableItem(Items.BAKED_POTATO,2);
        registerCookableItem(Items.MELON_SLICE,1);
        registerCookableItem(Items.BEETROOT,1);
        registerCookableItem(Items.CARROT,1);
        registerCookableItem(Items.COOKIE,1);
        registerCookableItem(Items.GOLDEN_APPLE,3);
        registerCookableItem(Items.ENCHANTED_GOLDEN_APPLE,5);
        registerCookableItem(Items.EGG,1);
        registerCookableItem(Items.BEEF,1);
        registerCookableItem(Items.COOKED_BEEF,2);
        registerCookableItem(Items.PORKCHOP,1);
        registerCookableItem(Items.COOKED_PORKCHOP,2);
        registerCookableItem(Items.CHICKEN,1);
        registerCookableItem(Items.COOKED_CHICKEN,2);
        registerCookableItem(Items.COOKED_COD,2);
        registerCookableItem(Items.COD,1);
        registerCookableItem(Items.SALMON,1);
        registerCookableItem(Items.COOKED_SALMON,2);
        registerCookableItem(Items.COOKED_RABBIT,2);
        registerCookableItem(Items.RABBIT,1);
        registerCookableItem(Items.MUSHROOM_STEM,2);
        registerCookableItem(Items.CAKE,4);
        registerCookableItem(Items.RABBIT_STEW,3);
        registerCookableItem(Items.MUTTON,1);
        registerCookableItem(Items.COOKED_MUTTON,2);
        registerCookableItem(Items.PUMPKIN_PIE,2);
        registerCookableItem(Items.BEETROOT_SOUP,2);
        registerCookableItem(Items.SPIDER_EYE,1);
        registerCookableItem(Items.PUFFERFISH,2);
        registerCookableItem(Items.DRIED_KELP,1);
        registerCookableItem(Items.POISONOUS_POTATO,1);
        registerCookableItem(Items.SWEET_BERRIES,1);
        registerCookableItem(Items.GLOW_BERRIES,1);
        registerCookableItem(Items.HONEY_BOTTLE,2);
        registerCookableItem(ModItems.MEATBALLS,3);
        registerCookableItem(ModItems.TORTILLA,3);
    }
    public static int MAX_INGREDIENTS = 5;
    public static IntProperty SAUCE_LEVEL = IntProperty.of("sauce_level",0, MAX_INGREDIENTS);
    private static VoxelShape SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(2,0,2,14,6,14),Block.createCuboidShape(1,6,1,15,8,15), BooleanBiFunction.OR);
    public CookingPotBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(SAUCE_LEVEL));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        Item itemHeld = player.getStackInHand(hand).getItem();
        if (hasFire(world,pos)&&COOKABLE_ITEMS.containsKey(itemHeld) && blockEntity instanceof CookingPotEntity cookingPotEntity) {
            int currlvl = state.get(SAUCE_LEVEL);
            if (currlvl < MAX_INGREDIENTS) {
                int i = currlvl + 1;
                cookingPotEntity.addIngredient(COOKABLE_ITEMS.get(itemHeld));
                if(!player.getAbilities().creativeMode) {
                    player.getStackInHand(hand).decrement(1);
                }
                world.setBlockState(pos, state.with(SAUCE_LEVEL, i));
                player.incrementStat(Stats.USED.getOrCreateStat(player.getMainHandStack().getItem()));
            }
            return ActionResult.success(world.isClient);
        } else if (player.getStackInHand(hand).getItem().equals(ModItems.SPAGHETTI_BOWL) && blockEntity instanceof CookingPotEntity cookingPotEntity) {
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            ItemStack item = new ItemStack(ModItems.SPAGHETTI_BOWL_SAUCED);
            item.setNbt(new NbtCompound());
            item.getNbt().put("SauceBonus",new NbtCompound());
            NbtCompound SauceBonus = item.getNbt().getCompound("SauceBonus");
            SauceBonus.putInt("Strength",cookingPotEntity.getBonus(0));
            SauceBonus.putInt("Health",cookingPotEntity.getBonus(1));
            SauceBonus.putInt("Speed",cookingPotEntity.getBonus(2));
            SauceBonus.putInt("Haste",cookingPotEntity.getBonus(3));
            SauceBonus.putInt("Duration",cookingPotEntity.getBonus(4));
            if (player.getStackInHand(hand).isEmpty()) {
                player.setStackInHand(hand,item);
            } else {
                player.giveItemStack(item);
            }
            world.setBlockState(pos,state.with(CookingPotBlock.SAUCE_LEVEL,0));
            player.incrementStat(Stats.USED.getOrCreateStat(player.getStackInHand(hand).getItem()));
            cookingPotEntity.clear();
        }
        return ActionResult.PASS;
    }

    public static boolean hasFire(WorldAccess world, BlockPos pos) {
        if(world.getBlockState(pos.down()).isOf(Blocks.CAMPFIRE)||world.getBlockState(pos.down()).isOf(Blocks.SOUL_CAMPFIRE)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    /* BLOCK ENTITY */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CookingPotEntity(pos, state);
    }
}

