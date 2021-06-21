package eu.pollux28.skis.block.metalpress;

import eu.pollux28.skis.setup.ModBlocks;
import eu.pollux28.skis.setup.ModContainerTypes;
import eu.pollux28.skis.utils.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MetalPressContainer extends Container {
    private final World level;
    private ItemStack input = ItemStack.EMPTY;
    Slot inputSlot;
    Slot outputSlot;
    private MetalPressTileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;


    public MetalPressContainer(int windowsID, World level, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(ModContainerTypes.METAL_PRESS_CONTAINER.get(), windowsID);
        this.level = level;
        this.tileEntity = (MetalPressTileEntity) level.getBlockEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);
        this.playerEntity = playerEntity;

        if(this.tileEntity!= null){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h->{
                inputSlot =addSlot(new SlotItemHandler(h,0,56,34));
                outputSlot = addSlot(new SlotItemHandler(h,1,116,35));
            });
        }
        layoutPlayerInventorySlots(8,84);
        trackPower();
        trackProgress();
    }

    private void trackProgress(){
        addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getPressingProgress();
            }

            @Override
            public void set(int p_221494_1_) {
                tileEntity.data.set(0,p_221494_1_);
            }
        });
        addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getTotalPressingTime();
            }

            @Override
            public void set(int p_221494_1_) {
                tileEntity.data.set(1,p_221494_1_);
            }
        });
    }

    private void trackPower() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    public int getTotalEnergy(){
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }
    public int getEnergyPercent(){
        int i = tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);

        return i!=0? getEnergy()* 100/i : 0;
    }

    public boolean isWorking(){
        return true;
    }
    public int getPressingProgressPercent(){
        int i = tileEntity.getPressingProgress();
        int j = tileEntity.getTotalPressingTime();
        return j != 0 && i != 0 ? i * 100 / j : 0;
    }


    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return stillValid(IWorldPosCallable.create(level,tileEntity.getBlockPos()),p_75145_1_, ModBlocks.METAL_PRESS_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else if(index==1){
                if(!this.moveItemStackTo(stack,2,38,true)){
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack,itemstack);

            } else {
                if (!this.moveItemStackTo(stack, 0, 1, false)) {

                    if (index < 28) {
                        if (!this.moveItemStackTo(stack, 29, 38, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < 37 && !this.moveItemStackTo(stack, 2, 29, false)) {
                        return ItemStack.EMPTY;
                    }else{
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerEntity, stack);
        }

        return itemstack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
