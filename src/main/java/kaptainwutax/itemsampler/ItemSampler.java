package kaptainwutax.itemsampler;

import kaptainwutax.itemsampler.init.ModBlockEntities;
import kaptainwutax.itemsampler.init.ModBlocks;
import kaptainwutax.itemsampler.init.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = ItemSampler.MOD_ID)
public class ItemSampler {

    public static final String MOD_ID = "item_sampler";
    public static final Logger LOGGER = LogManager.getLogger();

    public ItemSampler() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.REGISTRY.register(bus);
        ModItems.REGISTRY.register(bus);
        ModBlockEntities.REGISTRY.register(bus);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
