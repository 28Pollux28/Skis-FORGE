package eu.pollux28.skis.init;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.blocks.MachineBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Skis.MODID);
    public static final RegistryObject<Block> MACHINE_PLATE = BLOCKS.register("machine_block_plate", MachineBlock::new);
}
