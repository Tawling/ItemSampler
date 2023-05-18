package kaptainwutax.itemsampler.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public abstract class BaseBlockEntity extends TileEntity {

    public BaseBlockEntity(TileEntityType<?> type) {
        super(type);
    }

    public abstract CompoundNBT write(CompoundNBT nbt, UpdateType type);

    public abstract void read(CompoundNBT nbt, UpdateType type);

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        return this.write(super.write(nbt), UpdateType.SERVER);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.read(nbt, UpdateType.SERVER);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(super.getUpdateTag(), UpdateType.INITIAL_PACKET);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        this.read(nbt, UpdateType.INITIAL_PACKET);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), -1, this.write(new CompoundNBT(), UpdateType.UPDATE_PACKET));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        this.read(packet.getNbtCompound(), UpdateType.UPDATE_PACKET);
    }

    public void sendUpdates() {
        this.markDirty();

        if(this.world != null) {
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), Constants.BlockFlags.DEFAULT);
            this.world.notifyNeighborsOfStateChange(this.getPos(), this.getBlockState().getBlock());
        }
    }

    public enum UpdateType {
        SERVER, INITIAL_PACKET, UPDATE_PACKET
    }

}
