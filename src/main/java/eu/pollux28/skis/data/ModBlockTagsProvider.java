package eu.pollux28.skis.data;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.setup.ModBlocks;
import eu.pollux28.skis.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_i48256_1_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_i48256_1_, Skis.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.ORE_SILVER).add(ModBlocks.SILVER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORE_SILVER);
        tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.SILVER_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER);
    }
}
