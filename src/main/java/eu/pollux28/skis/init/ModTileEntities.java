package eu.pollux28.skis.init;

import eu.pollux28.skis.Skis;
import eu.pollux28.skis.tile.TileEntityTest;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Skis.MODID);
    public static final RegistryObject<TileEntityType<?>> TEST_TILE_ENTITY = TILE_ENTITIES.register("test",()-> TileEntityType.Builder.of(TileEntityTest::new, Blocks.BLUE_CONCRETE_POWDER).build(null));
}
