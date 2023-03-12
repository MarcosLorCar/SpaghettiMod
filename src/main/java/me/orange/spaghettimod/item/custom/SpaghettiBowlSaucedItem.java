package me.orange.spaghettimod.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpaghettiBowlSaucedItem extends Item {

    public SpaghettiBowlSaucedItem() {
        super(new FabricItemSettings().food(new FoodComponent.Builder().hunger(20).saturationModifier(10).build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        NbtCompound SauceBonus = stack.getNbt().getCompound("SauceBonus");
        int Duration = SauceBonus.getInt("Duration");
        int[] bonuses = new int[]{
                SauceBonus.getInt("Strength"),
                SauceBonus.getInt("Health"),
                SauceBonus.getInt("Speed"),
                SauceBonus.getInt("Haste")
        };
        StatusEffect[] bonusEfects = new StatusEffect[]{
                StatusEffects.STRENGTH,
                StatusEffects.HEALTH_BOOST,
                StatusEffects.SPEED,
                StatusEffects.HASTE
        };
        for (int i = 0; i < bonuses.length; i++) {
            if (bonuses[i] > 0) {
                user.addStatusEffect(new StatusEffectInstance(bonusEfects[i],Duration*20, (int) Math.floor((bonuses[i]-1)/2)));
            }
        }
        //user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,Duration,SauceBonus.getInt("Strength")));
        return itemStack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.hasNbt()) {
            Text firstRow, secondRow;
            NbtCompound SauceBonus = stack.getNbt().getCompound("SauceBonus");
            int Duration = SauceBonus.getInt("Duration");
            firstRow = Text.literal("Strength: "+ SauceBonus.getInt("Strength")).formatted(Formatting.BOLD, Formatting.DARK_RED).append(Text.literal(" Health: "+ SauceBonus.getInt("Health")).formatted(Formatting.BOLD, Formatting.GREEN));
            secondRow = Text.literal("Speed: "+ SauceBonus.getInt("Speed")).formatted(Formatting.BOLD, Formatting.BLUE).append(Text.literal(" Haste: "+ SauceBonus.getInt("Haste")).formatted(Formatting.BOLD, Formatting.YELLOW));
            tooltip.add(firstRow);
            tooltip.add(secondRow);
            tooltip.add(Text.literal("Duration: "+ ((int) Math.floor(Duration/60)) +":"+((int) Math.floor(Duration%60))).formatted(Formatting.BOLD, Formatting.GRAY));
        }
    }
}
