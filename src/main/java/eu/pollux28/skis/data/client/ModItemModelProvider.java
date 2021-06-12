package eu.pollux28.skis.data.client;

import eu.pollux28.skis.Skis;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Skis.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("silver_block",modLoc("block/silver_block"));
        withExistingParent("silver_ore",modLoc("block/silver_ore"));
        //orientable("metal_press",modLoc("block/metal_press_side"),modLoc("block/metal_press_front"),modLoc("block/metal_press_top"));
        withExistingParent("metal_press", modLoc("block/metal_press"));
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        builder(itemGenerated, "silver_ingot");
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/"+name);
    }
}
