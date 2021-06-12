package eu.pollux28.skis.data;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.data.client.ModBlockStateProvider;
import eu.pollux28.skis.data.client.ModItemModelProvider;
import eu.pollux28.skis.data.loot.ModLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Skis.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators(){}
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(new ModBlockStateProvider(gen,existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTagsProvider);
        gen.addProvider(new ModItemTagsProvider(gen,blockTagsProvider,existingFileHelper));
        gen.addProvider(new ModLootTableProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));
    }
}
