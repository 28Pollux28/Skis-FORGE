package eu.pollux28.skis.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import eu.pollux28.skis.setup.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class PressingRecipe implements IRecipe<IInventory> {
    private final IRecipeSerializer<PressingRecipe> serializer;
    private final IRecipeType<PressingRecipe> type;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int pressingTime;
    public PressingRecipe(IRecipeType<PressingRecipe> type,IRecipeSerializer<PressingRecipe> serializer,ResourceLocation recipeId,String group ,Ingredient ingredient,ItemStack result,int pressingTime) {
        this.type = type;
        this.serializer = serializer;
        this.id = recipeId;
        this.group = group;
        this.ingredient= ingredient;
        this.result = result;
        this.pressingTime = pressingTime;
    }

    public PressingRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, int pressingTime) {
        this(ModRecipes.Types.PRESSING,ModRecipes.Serializers.PRESSING.get(),id,group,ingredient,result,pressingTime);
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return this.ingredient.test(inv.getItem(0));
    }

    public int getPressingTime() {
        return this.pressingTime;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return this.type;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return this.result.copy();
    }

    public static class PressingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PressingRecipe>{
        private final int defaultPressingTime;
        private final PressingRecipeSerializer.IFactory<PressingRecipe> factory;

        public PressingRecipeSerializer(IFactory<PressingRecipe> factory, int defaultPressingTime) {
            this.factory = factory;
            this.defaultPressingTime = defaultPressingTime;
        }


        @Override
        public PressingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getAsString(json, "group", "");
            JsonElement jsonelement = (JsonElement)(JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.getAsJsonArray(json, "ingredient") : JSONUtils.getAsJsonObject(json, "ingredient"));
            Ingredient ingredient = Ingredient.fromJson(jsonelement);
            //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
            if (!json.has("result")) throw new JsonSyntaxException("Missing result, expected to find a string or object");
            ItemStack itemstack;
            if (json.get("result").isJsonObject()) itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            else {
                String s1 = JSONUtils.getAsString(json, "result");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                    return new IllegalStateException("Item: " + s1 + " does not exist");
                }));
            }
            int i = JSONUtils.getAsInt(json, "cookingtime", this.defaultPressingTime);
            return this.factory.create(recipeId, s, ingredient, itemstack,  i);
        }

        @Nullable
        @Override
        public PressingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readUtf(32767);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            float f = buffer.readFloat();
            int i = buffer.readVarInt();
            return this.factory.create(recipeId, s, ingredient, itemstack, i);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PressingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeVarInt(recipe.pressingTime);
        }
        
        public interface IFactory<PressingRecipe>{
            eu.pollux28.skis.crafting.recipe.PressingRecipe create(ResourceLocation id, String group,Ingredient ingredient, ItemStack result, int pressingTime);
        } 
    }
}
