package eu.pollux28.skis.data.recipes;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.setup.ModBlocks;
import eu.pollux28.skis.setup.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;


public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.SILVER_INGOT.get(), 9)
                .requires(ModBlocks.SILVER_BLOCK.get())
                .unlockedBy("has_item",has(ModItems.SILVER_INGOT.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.SILVER_BLOCK.get())
                .define('#',ModItems.SILVER_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.SILVER_INGOT.get()))
                .save(consumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.SILVER_ORE.get()),ModItems.SILVER_INGOT.get(),0.7f,200)
                .unlockedBy("has_item",has(ModBlocks.SILVER_ORE.get()))
                .save(consumer,modid("silver_ingot_smelting"));
        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.SILVER_ORE.get()),ModItems.SILVER_INGOT.get(),0.7f,100)
                .unlockedBy("has_item",has(ModBlocks.SILVER_ORE.get()))
                .save(consumer,modid("silver_ingot_blasting"));
        PressingRecipeBuilder.builder(Ingredient.of(ModItems.SILVER_INGOT.get()), Items.STONE_BUTTON,1,40,20)
                .unlocks("has_item",has(ModItems.SILVER_INGOT.get()))
                .save(consumer,"silver_plate_pressing");
    }

    private static ResourceLocation modid(String path){
        return new ResourceLocation(Skis.MOD_ID,path);
    }
}
