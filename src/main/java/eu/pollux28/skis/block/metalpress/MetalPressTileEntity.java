package eu.pollux28.skis.block.metalpress;

import eu.pollux28.skis.crafting.recipe.PressingRecipe;
import eu.pollux28.skis.setup.ModRecipes;
import eu.pollux28.skis.setup.ModTileEntityTypes;
import eu.pollux28.skis.utils.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalPressTileEntity extends TileEntity implements ITickableTileEntity {
    private final int MAX_ENERGY_STORED =10000;
    private int progress = 0;
    private int totalPressingTime;
    public final IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return progress;
                case 1:
                    return totalPressingTime;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    progress = value;
                    break;
                case 1:
                    totalPressingTime = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    private ItemStackHandler itemHandler = createHandler();
    private CustomEnergyStorage energyStorage = createEnergy();
    private LazyOptional<IItemHandler> itemHandlerOptional = LazyOptional.of(()->itemHandler);
    private LazyOptional<IEnergyStorage> energyHandlerOptional = LazyOptional.of(()->energyStorage);
    private IItemHandler itemHopperHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return itemHandler.getSlots();
        }

        @Nonnull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return itemHandler.getStackInSlot(slot);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return itemHandler.insertItem(slot,stack,simulate);
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot == 0? ItemStack.EMPTY: itemHandler.extractItem(slot,amount,simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return itemHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return itemHandler.isItemValid(slot,stack);
        }
    };
    private LazyOptional<IItemHandler> itemHopperHandlerOptional = LazyOptional.of(()->itemHopperHandler);


    public MetalPressTileEntity() {
        super(ModTileEntityTypes.METAL_PRESS.get());
        this.energyStorage.setEnergy(this.MAX_ENERGY_STORED);
    }



    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) {
            return;
        }

        PressingRecipe recipe = getRecipe();
        if (recipe != null) {
            doWork(recipe);
        } else {
            stopWork();
        }
        this.setChanged();
    }

    private void doWork(PressingRecipe recipe){
        assert this.level!=null;

        ItemStack currentProd = itemHandler.getStackInSlot(1);
        ItemStack output = getWorkOutput(recipe);

        if(!currentProd.isEmpty()) {
            int newCount = currentProd.getCount() + output.getCount();
            if (!ItemStack.tagMatches(currentProd, output) || newCount > output.getMaxStackSize()) {
                stopWork();
                return;
            }
        }
        if(this.progress < recipe.getPressingTime()){
            if(this.energyStorage.getEnergyStored()>=recipe.getEnergyConsumption()){
                this.progress++;
                energyStorage.consumeEnergy(recipe.getEnergyConsumption());
            }else{
                stopWork();
            }
        }
        if(this.progress >= recipe.getPressingTime()){
            finishWork(currentProd, output);
        }

    }

    private void finishWork(ItemStack currentProd, ItemStack output) {
        if(!currentProd.isEmpty()){
            currentProd.grow(output.getCount());
        }else{
            this.itemHandler.setStackInSlot(1,output);
        }
        this.progress = 0;
        this.itemHandler.extractItem(0,1,false) ;//removeItem(0,1);
    }

    private void stopWork(){
        this.progress = MathHelper.clamp(this.progress-2,0,this.getTotalPressingTime());
    }

    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 11, this.getUpdateTag());
    }

    public int getPressingProgress(){
        return progress;
    }

    public int getTotalPressingTime() {
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.Types.PRESSING, new RecipeWrapper(this.itemHandler), this.level).map(PressingRecipe::getPressingTime).orElse(40);
    }

    @Nullable
    private PressingRecipe getRecipe() {
        if (this.level == null || itemHandler.getStackInSlot(0).isEmpty()) {
            return null;
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.Types.PRESSING, new RecipeWrapper(this.itemHandler), this.level).orElse(null);
    }

    private ItemStack getWorkOutput(@Nullable PressingRecipe recipe){
        if(recipe != null){
            return recipe.assemble(new RecipeWrapper(this.itemHandler));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        invalidateCaps();
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
        itemHandler.deserializeNBT(tags.getCompound("inv"));
        energyStorage.deserializeNBT(tags.getCompound("energy"));

        this.progress = tags.getInt("Progress");
        this.totalPressingTime = tags.getInt("totalPressingTime");
        super.load(state, tags);
    }

    @Override
    public CompoundNBT save(CompoundNBT tags) {
        super.save(tags);
        tags.put("inv",itemHandler.serializeNBT());
        tags.put("energy",energyStorage.serializeNBT());
        tags.putInt("Progress",this.progress);
        tags.putInt("totalPressingTime",this.totalPressingTime);
        return super.save(tags);
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(MAX_ENERGY_STORED, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    private ItemStackHandler createHandler(){
        return new ItemStackHandler(2){
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot !=1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }


            @Override
            protected void onContentsChanged(int slot) {
                setChanged();

            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)){
            if(side == null){
                return itemHandlerOptional.cast();
            }else{
                return itemHopperHandlerOptional.cast();
            }

        }
        else if(cap.equals(CapabilityEnergy.ENERGY)){
            return energyHandlerOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        energyHandlerOptional.invalidate();
        itemHandlerOptional.invalidate();
        itemHopperHandlerOptional.invalidate();
    }
}
