package kaptainwutax.itemsampler.init;

import kaptainwutax.itemsampler.ItemSampler;
import kaptainwutax.itemsampler.block.entity.ItemSamplerBlockEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<TileEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ItemSampler.MOD_ID);

    public static final RegistryObject<TileEntityType<ItemSamplerBlockEntity>> ITEM_SAMPLER = registerBlockEntity("item_sampler",
            () -> TileEntityType.Builder.create(ItemSamplerBlockEntity::new, ModBlocks.ITEM_SAMPLER.get()).build(null));

    private static <T extends TileEntityType<?>> RegistryObject<T> registerBlockEntity(String id, Supplier<T> blockEntity) {
        return ModBlockEntities.REGISTRY.register(id, blockEntity);
    }

}
