package eu.pollux28.skis.data.recipes;

import com.google.gson.JsonObject;
import eu.pollux28.skis.Skis;
import eu.pollux28.skis.setup.ModRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class PressingRecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final int pressingTime;
    private final int energyConsumption;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;

    public PressingRecipeBuilder( Ingredient ingredient, IItemProvider result, int count, int pressingTime, int energyConsumption) {

        this.result = result.asItem();
        this.ingredient = ingredient;
        this.count = count;
        this.pressingTime = pressingTime;
        this.energyConsumption = energyConsumption;
    }

    public static PressingRecipeBuilder builder(Ingredient ingredient, IItemProvider result) {
        return new PressingRecipeBuilder( ingredient, result, 1,40,20);
    }
    public static PressingRecipeBuilder builder(Ingredient ingredient, IItemProvider result, int count) {
        return new PressingRecipeBuilder( ingredient, result, count,40,20);
    }

    public static PressingRecipeBuilder builder(Ingredient ingredient, IItemProvider result, int count, int pressingTime) {
        return new PressingRecipeBuilder( ingredient, result, count,pressingTime,20);
    }

    public static PressingRecipeBuilder builder(Ingredient ingredient, IItemProvider result, int count, int pressingTime,int energyConsumption) {
        return new PressingRecipeBuilder( ingredient, result, count,pressingTime,energyConsumption);
    }

    public PressingRecipeBuilder unlocks(String criteria, ICriterionInstance criterionInstance) {
        this.advancement.addCriterion(criteria, criterionInstance);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> recipeConsumer, String identifier) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        if ((new ResourceLocation(identifier)).equals(resourcelocation)) {
            throw new IllegalStateException("Pressing Recipe " + identifier + " should remove its 'save' argument");
        } else {
            this.save(recipeConsumer, new ResourceLocation(Skis.MOD_ID,identifier));
        }
    }

    public void save(Consumer<IFinishedRecipe> recipeConsumer, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(new ResourceLocation(Skis.MOD_ID,"recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(IRequirementsStrategy.OR);
        recipeConsumer.accept(new PressingRecipeBuilder.Result(resourceLocation, this.group == null ? "" : this.group,this.ingredient,this.result, this.count, this.pressingTime,this.energyConsumption, this.advancement, new ResourceLocation(resourceLocation.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + resourceLocation.getPath())));
    }

    private void ensureValid(ResourceLocation p_218646_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_218646_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Item result;
        private final int count;
        private final int pressingTime;
        private final int energyConsumption;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id,  String group, Ingredient ingredient, Item result, int count,int pressingTime,int energyConsumption, Advancement.Builder p_i50574_7_, ResourceLocation p_i50574_8_) {
            this.id = id;
            this.group = group;
            this.ingredient = ingredient;
            this.result = result;
            this.count = count;
            this.pressingTime = pressingTime;
            this.energyConsumption = energyConsumption;
            this.advancement = p_i50574_7_;
            this.advancementId = p_i50574_8_;
        }

        public void serializeRecipeData(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.add("ingredient", this.ingredient.toJson());
            jsonObject.addProperty("result", Registry.ITEM.getKey(this.result).toString());
            jsonObject.addProperty("count", this.count);
            jsonObject.addProperty("pressingTime",this.pressingTime);
            jsonObject.addProperty("energyConsumption", this.energyConsumption);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public IRecipeSerializer<?> getType() {
            return ModRecipes.Serializers.PRESSING.get();
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
