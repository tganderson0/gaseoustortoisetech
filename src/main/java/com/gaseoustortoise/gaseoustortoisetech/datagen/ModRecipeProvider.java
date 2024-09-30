package com.gaseoustortoise.gaseoustortoisetech.datagen;

import com.gaseoustortoise.gaseoustortoisetech.GaseoustortoiseTech;
import com.gaseoustortoise.gaseoustortoisetech.block.ModBlocks;
import com.gaseoustortoise.gaseoustortoisetech.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BISMUTH_BLOCK.get())
                .pattern("bbb")
                .pattern("bbb")
                .pattern("bbb")
                .define('b', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 9)
                .requires(ModBlocks.BISMUTH_BLOCK)
                .unlockedBy("has_bismuth_block", has(ModBlocks.BISMUTH_BLOCK))
                .save(recipeOutput, "gaseoustortoisetech:bismuth_from_bismuth_block"); // save has a second parameter if there's multiple ways to make the same thing

        List<ItemLike> BISMUTH_SMELTABLES = List.of(ModItems.RAW_BISMUTH, ModBlocks.BISMUTH_ORE, ModBlocks.BISMUTH_DEEPSLATE_ORE);
        oreSmelting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH.get(), 0.25f, 200, "bismuth");
        oreBlasting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH.get(), 0.25f, 100, "bismuth");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup){
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup){
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemLike : pIngredients) {
            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemLike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemLike), has(itemLike))
                    .save(recipeOutput, GaseoustortoiseTech.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemLike));
        }
    }
}
