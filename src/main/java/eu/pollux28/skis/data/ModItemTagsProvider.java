package eu.pollux28.skis.data;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.setup.ModItems;
import eu.pollux28.skis.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator gen, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, blockTagsProvider, Skis.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(ModTags.Blocks.ORE_SILVER, ModTags.Items.ORES_SILVER);
        copy(Tags.Blocks.ORES,Tags.Items.ORES);
        copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        tag(ModTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(ModTags.Items.INGOTS_SILVER);
    }
}
