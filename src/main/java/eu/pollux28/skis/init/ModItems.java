package eu.pollux28.skis.init;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.utils.ModItemGroup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Skis.MODID);
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> TIN_PLATE = ITEMS.register("tin_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> ALUMINIUM_PLATE = ITEMS.register("aluminium_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> DIAMOND_PLATE = ITEMS.register("diamond_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> LEAD_PLATE = ITEMS.register("lead_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));
    public static final RegistryObject<Item> EMERALD_PLATE = ITEMS.register("emerald_plate",()-> new Item(new Item.Properties().tab(ModItemGroup.SKIS_TAB)));


}
