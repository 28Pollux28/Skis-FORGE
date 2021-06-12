package eu.pollux28.skis.data.client;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, Skis.MOD_ID,existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SILVER_BLOCK.get());
        simpleBlock(ModBlocks.SILVER_ORE.get());
        horizontalBlock(ModBlocks.METAL_PRESS_BLOCK.get(), modLoc("block/metal_press_side"),modLoc("block/metal_press_front"),modLoc("block/metal_press_top"));
    }
}
