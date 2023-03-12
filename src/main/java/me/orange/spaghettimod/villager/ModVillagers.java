package me.orange.spaghettimod.villager;

import me.orange.spaghettimod.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModVillagers {



    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD,1),
                            new ItemStack(ModItems.TOMATO_SEEDS,5),
                            8,2,0.02f
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.TOMATO,8),
                            new ItemStack(Items.EMERALD,1),
                            8,2,0.02f
                    ));
                });
    }
}
