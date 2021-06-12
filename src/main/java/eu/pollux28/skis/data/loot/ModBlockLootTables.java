package eu.pollux28.skis.data.loot;

import eu.pollux28.skis.setup.ModBlocks;
import eu.pollux28.skis.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.fml.RegistryObject;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        dropSelf(ModBlocks.SILVER_BLOCK.get());
        dropSelf(ModBlocks.SILVER_ORE.get());
        dropSelf(ModBlocks.METAL_PRESS_BLOCK.get());
        /*add(block, (p_123)->{
            return createSilkTouchDispatchTable(p_123,applyExplosionDecay(p_123, ItemLootEntry.lootTableItem(Items.BROWN_MUSHROOM).apply(SetCount.setCount(RandomValueRange.between(4.0f,5.0f))).apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
        });*/
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}
