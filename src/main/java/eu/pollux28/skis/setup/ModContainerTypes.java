package eu.pollux28.skis.setup;

import eu.pollux28.skis.block.metalpress.MetalPressContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModContainerTypes {
    public static final RegistryObject<ContainerType<MetalPressContainer>> METAL_PRESS_CONTAINER = register("machine_block_plate_container", ()-> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getCommandSenderWorld();
        return new MetalPressContainer(windowId, world, pos, inv, inv.player);
    }));

    static void register(){
    }
    private static <T extends Container>RegistryObject<ContainerType<T>> register(String name, Supplier<ContainerType<T>> serializer){
        return Registration.CONTAINERS.register(name, serializer);
    }
}
