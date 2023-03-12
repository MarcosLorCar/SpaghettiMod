package me.orange.spaghettimod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;

public class CookingPotEntity extends BlockEntity implements Clearable {
    int[] bonuses = {0,0,0,0,0};
    public CookingPotEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COOKING_POT, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("SauceBonus",new NbtCompound());
        NbtCompound SauceBonus = nbt.getCompound("SauceBonus");
        SauceBonus.putInt("Strength",bonuses[0]);
        SauceBonus.putInt("Health",bonuses[1]);
        SauceBonus.putInt("Speed",bonuses[2]);
        SauceBonus.putInt("Haste",bonuses[3]);
        SauceBonus.putInt("Duration",bonuses[4]);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtCompound SauceBonus = nbt.getCompound("SauceBonus");
        bonuses[0] = SauceBonus.getInt("Strength");
        bonuses[1] = SauceBonus.getInt("Health");
        bonuses[2] = SauceBonus.getInt("Speed");
        bonuses[3] = SauceBonus.getInt("Haste");
        bonuses[4] = SauceBonus.getInt("Duration");
    }

    public void addIngredient(int quality) {
        for (int i = 0; i < bonuses.length; i++) {
            if (i < (bonuses.length - 1)) {
                bonuses[i] += UniformIntProvider.create(quality - 1, quality).get(Random.create());
            } else {
                Math.pow(quality,3);
                bonuses[i] += UniformIntProvider.create(quality, quality + 10).get(Random.create());
            }
        }
        this.markDirty();
    }

    public int getBonus(int index) {
        return bonuses[index];
    }

    @Override
    public void clear() {
        bonuses = new int[5];
    }
}
