package kaptainwutax.itemsampler.block;

import kaptainwutax.itemsampler.block.entity.ItemSamplerBlockEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class ItemSamplerBlock extends ContainerBlock {

    protected static VoxelShape SHAPE;

    static {
        SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
        SHAPE = VoxelShapes.combineAndSimplify(SHAPE, Block.makeCuboidShape(0, 3, 3, 16, 13, 13), IBooleanFunction.ONLY_FIRST);
        SHAPE = VoxelShapes.combineAndSimplify(SHAPE, Block.makeCuboidShape(3, 0, 3, 13, 16, 13), IBooleanFunction.ONLY_FIRST);
        SHAPE = VoxelShapes.combineAndSimplify(SHAPE, Block.makeCuboidShape(3, 3, 0, 13, 13, 16), IBooleanFunction.ONLY_FIRST);
    }

    public ItemSamplerBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(world instanceof ServerWorld && hand == Hand.MAIN_HAND) {
            ItemStack current = player.getHeldItem(hand);
            TileEntity blockEntity = world.getTileEntity(pos);

            if(blockEntity instanceof ItemSamplerBlockEntity) {
                ItemSamplerBlockEntity sampler = (ItemSamplerBlockEntity)blockEntity;
                ItemStack present = sampler.getInventory().getStackInSlot(0);

                if(present.isEmpty()) {
                    player.setHeldItem(hand, sampler.getInventory().insertItem(0, current, false));
                    current = sampler.getInventory().getStackInSlot(0);

                    if(current.getItem() instanceof BlockItem) {
                        BlockItem blockItem = ((BlockItem)current.getItem());
                        Block block = blockItem.getBlock();
                        SoundEvent sound = block.getDefaultState().getSoundType(world, pos, player).getPlaceSound();
                        SoundType soundType = block.getDefaultState().getSoundType(world, pos, player);
                        world.playSound(null, pos, sound, SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
                    } else {
                        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_PLACE, SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
                    }

                    return ActionResultType.CONSUME;
                } else {
                    if(current.isEmpty()) {
                        player.setHeldItem(hand, present.copy());
                        sampler.getInventory().setStackInSlot(0, ItemStack.EMPTY);
                    } else {
                        present = present.copy();
                        player.inventory.placeItemBackInInventory(world, present);
                        sampler.getInventory().setStackInSlot(0, ItemStack.EMPTY);
                    }

                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    return ActionResultType.CONSUME;
                }
            }
        }

        return ActionResultType.CONSUME;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.isIn(newState.getBlock())) return;
        TileEntity blockEntity = world.getTileEntity(pos);

        if(blockEntity instanceof ItemSamplerBlockEntity) {
            ItemSamplerBlockEntity sampler = (ItemSamplerBlockEntity)blockEntity;

            for (int i = 0; i < sampler.getInventory().getSlots(); i++) {
                ItemStack stack = sampler.getInventory().getStackInSlot(i);
                if(!stack.isEmpty()) Block.spawnAsEntity(world, pos, stack);
            }

            world.updateComparatorOutputLevel(pos, this);
        }

        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new ItemSamplerBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
        TileEntity blockEntity = world.getTileEntity(pos);

        if(blockEntity instanceof ItemSamplerBlockEntity) {
            return ((ItemSamplerBlockEntity)blockEntity).getComparatorPower();
        }

        return 0;
    }

}
