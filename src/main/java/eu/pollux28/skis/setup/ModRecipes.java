package eu.pollux28.skis.setup;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.crafting.recipe.PressingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModRecipes {
    public static class Types{
        static void register(){}
        public static final IRecipeType<PressingRecipe> PRESSING = IRecipeType.register(Skis.MOD_ID + "pressing");
    }

    public static class Serializers{
        static void register(){}
        public static final RegistryObject<IRecipeSerializer<PressingRecipe>> PRESSING = register("pressing", PressingRecipe.PressingRecipeSerializer::new);

        private static <T extends IRecipe<?>>RegistryObject<IRecipeSerializer<T>> register(String name, Supplier<IRecipeSerializer<T>> serializer){
            return Registration.RECIPE_SERIALIZER.register(name, serializer);
        }
    }

    static void register(){
        Serializers.register();
        Types.register();
    }
}
