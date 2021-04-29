package eu.pollux28.skis.init;

import eu.pollux28.skis.Skis;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skis.MODID);
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate",()-> new Item(new Item.Properties()));
}
