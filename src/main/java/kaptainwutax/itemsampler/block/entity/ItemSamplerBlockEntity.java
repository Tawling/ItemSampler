package kaptainwutax.itemsampler.block.entity;

import kaptainwutax.itemsampler.init.ModBlockEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemSamplerBlockEntity extends BaseBlockEntity {

    private final ItemStackHandler inventory = new ItemHandler(this);
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> this.inventory);

    public ItemSamplerBlockEntity() {
        super(ModBlockEntities.ITEM_SAMPLER.get());
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    public int getComparatorPower() {
        ItemStack stack = this.inventory.getStackInSlot(0);
        if(stack.isEmpty()) return 0;
        return (int)Math.round(17 - 2 * Math.sqrt(stack.getMaxStackSize()));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt, UpdateType type) {
        nbt.put("inventory", this.inventory.serializeNBT());
        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt, UpdateType type) {
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    public void sendUpdates() {
        if(this.world != null) {
            this.world.updateComparatorOutputLevel(this.getPos(), this.getBlockState().getBlock());
        }

        super.sendUpdates();
    }

    private static class ItemHandler extends ItemStackHandler {
        private final ItemSamplerBlockEntity parent;

        public ItemHandler(ItemSamplerBlockEntity parent) {
            super(1);
            this.parent = parent;
        }

        @Override
        protected void onContentsChanged(int slot) {
            this.parent.sendUpdates();
        }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }
    }

}
