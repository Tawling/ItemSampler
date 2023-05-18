package kaptainwutax.itemsampler.event;

import kaptainwutax.itemsampler.block.renderer.ItemSamplerBlockRenderer;
import kaptainwutax.itemsampler.init.ModBlockEntities;
import kaptainwutax.itemsampler.init.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.ITEM_SAMPLER.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public static void onBlockRendererRegister(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ModBlockEntities.ITEM_SAMPLER.get(), ItemSamplerBlockRenderer::new);
    }

}
