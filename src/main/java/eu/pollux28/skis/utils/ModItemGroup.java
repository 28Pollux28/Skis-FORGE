package eu.pollux28.skis.utils;

import eu.pollux28.skis.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup SKIS_TAB = new ItemGroup("skis_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.IRON_PLATE.get());
        }
    };
}
