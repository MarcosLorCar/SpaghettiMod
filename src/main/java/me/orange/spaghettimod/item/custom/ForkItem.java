package me.orange.spaghettimod.item.custom;

import com.google.common.eventbus.Subscribe;
import me.orange.spaghettimod.block.ModBlocks;
import me.orange.spaghettimod.block.custom.SpaghettiOreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EventListener;
import java.util.Optional;

public class ForkItem extends Item {
    public ForkItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = new ItemStack(this);
        itemStack.setNbt(new NbtCompound());
        itemStack.getNbt().putBoolean("Forking",false);
        return itemStack;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            if (user.isSneaking()) {
                if (user.getStackInHand(hand).getOrCreateNbt().getBoolean("Forking")) {
                    user.getStackInHand(hand).getOrCreateNbt().putBoolean("Forking",false);
                } else {
                    if (user.getStackInHand(hand).getDamage()<getMaxDamage()-2) {
                        user.getStackInHand(hand).getOrCreateNbt().putBoolean("Forking",true);
                    }
                }
                user.getItemCooldownManager().set(this,20);
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()&&context.getPlayer().getMainHandStack().getOrCreateNbt().getBoolean("Forking")) {
            BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
            if (blockState.get(SpaghettiOreBlock.FORKING_TIME) == SpaghettiOreBlock.max_forking_time) {
                context.getWorld().breakBlock(context.getBlockPos(), true, context.getPlayer());
            } else {
                blockState = blockState.with(SpaghettiOreBlock.FORKING_TIME,blockState.get(SpaghettiOreBlock.FORKING_TIME)+1);
                blockState = switch (blockState.get(SpaghettiOreBlock.FORKING_TIME)) {
                    case 1 -> blockState.with(SpaghettiOreBlock.FORKING_STAGE, 1);
                    case 10 -> blockState.with(SpaghettiOreBlock.FORKING_STAGE, 2);
                    case 20 -> blockState.with(SpaghettiOreBlock.FORKING_STAGE, 3);
                    default -> blockState;
                };
                context.getWorld().addBlockBreakParticles(context.getBlockPos(),blockState);
                context.getWorld().setBlockState(context.getBlockPos(),blockState);
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x0000FF;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && stack.getOrCreateNbt().getBoolean("Forking")) {
            if (stack.getDamage()<(getMaxDamage()-1)) {
                stack.setDamage(stack.getDamage()+1);
            } else {
                stack.getOrCreateNbt().putBoolean("Forking",false);
            }
        }
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }
}
