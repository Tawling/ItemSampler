package kaptainwutax.itemsampler.init;

import kaptainwutax.itemsampler.ItemSampler;
import kaptainwutax.itemsampler.block.ItemSamplerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ItemSampler.MOD_ID);

    public static final RegistryObject<ItemSamplerBlock> ITEM_SAMPLER = registerBlockItem("item_sampler",
            () -> new ItemSamplerBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .harvestTool(ToolType.AXE)
                    .hardnessAndResistance(3.0F, 4.8F)
                    .notSolid()),
            holder -> new BlockItem(holder.get(), new Item.Properties()
                    .group(ItemGroup.REDSTONE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String id, Supplier<T> block) {
        return ModBlocks.REGISTRY.register(id, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block, Function<RegistryObject<T>, Item> item) {
        RegistryObject<T> holder = registerBlock(name, block);
        ModItems.REGISTRY.register(name, () -> item.apply(holder));
        return holder;
    }

}
