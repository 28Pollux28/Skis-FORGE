package eu.pollux28.skis.tile;

import eu.pollux28.skis.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityTest extends TileEntity implements ITickableTileEntity {

    private int counter=0;

    public TileEntityTest(){
        super(ModTileEntities.TEST_TILE_ENTITY.get());
    }

    @Override
    public void load(BlockState blockState, CompoundNBT compoundNBT) {
        super.load(blockState, compoundNBT);
        this.setCounter(compoundNBT.getInt("counter"));
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.putInt("counter",this.getCounter());
        return compoundNBT;
    }

    @Override
    public void tick() {
        setCounter(getCounter()+1);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
