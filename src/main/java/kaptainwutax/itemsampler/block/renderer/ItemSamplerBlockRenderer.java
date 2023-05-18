package kaptainwutax.itemsampler.block.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import kaptainwutax.itemsampler.block.entity.ItemSamplerBlockEntity;
import kaptainwutax.itemsampler.client.ClientTicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class ItemSamplerBlockRenderer extends TileEntityRenderer<ItemSamplerBlockEntity> {

    public ItemSamplerBlockRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ItemSamplerBlockEntity entity, float tickDelta, MatrixStack matrices, IRenderTypeBuffer buffer, int light, int overlay) {
        matrices.push();
        ItemStack stack = entity.getInventory().getStackInSlot(0);

        if(!stack.isEmpty()) {
            if(stack.getItem() instanceof BlockItem) {
                matrices.translate(0.5D, 0.18D, 0.5D);
                matrices.scale(1.5F, 1.5F, 1.5F);
            } else {
                matrices.translate(0.5D, 0.3D, 0.5D);
                matrices.scale(1.2F, 1.2F, 1.2F);
            }

            matrices.rotate(Vector3f.YP.rotationDegrees((ClientTicker.TICK + tickDelta) * 5.0F % 360.0F));
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, light, overlay, matrices, buffer);
        }

        matrices.pop();
    }

}
