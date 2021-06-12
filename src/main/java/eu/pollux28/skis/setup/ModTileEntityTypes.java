package eu.pollux28.skis.setup;

import eu.pollux28.skis.block.metalpress.MetalPressTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModTileEntityTypes {
    static void register(){}

    public static final RegistryObject<TileEntityType<MetalPressTileEntity>>METAL_PRESS = register("metal_press",MetalPressTileEntity::new,ModBlocks.METAL_PRESS_BLOCK);

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block){
        return Registration.TILE_ENTITIES.register(name, () ->{
            //noinspection ConstantConditions
            return TileEntityType.Builder.of(factory,block.get()).build(null);
        });
    }
}
