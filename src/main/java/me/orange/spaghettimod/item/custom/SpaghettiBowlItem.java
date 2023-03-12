package me.orange.spaghettimod.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.world.World;

public class SpaghettiBowlItem extends Item {
    public SpaghettiBowlItem() {
        super(new FabricItemSettings().maxCount(1)
                .food(new FoodComponent.Builder().hunger(10).meat().saturationModifier(10).build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()&&user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            player.giveItemStack(new ItemStack(Items.BOWL));
        }
        return super.finishUsing(stack, world, user);
    }
}
