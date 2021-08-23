package init;

import blocks.EarthEssenceBlock;
import blocks.FireEssenceBlock;
import blocks.WaterEssenceBlock;
import global.Elementum;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class ModBlocks {
    public static FireEssenceBlock fireEssenceBlock;
    public static BlockItem fireEssenceBlockItem;

    public static WaterEssenceBlock waterEssenceBlock;
    public static BlockItem waterEssenceBlockItem;

    public static EarthEssenceBlock earthEssenceBlock;
    public static BlockItem earthEssenceBlockItem;

    @SubscribeEvent
    public static void onBlocksRegistration(final RegistryEvent.Register<Block> blockRegisterEvent) {
        fireEssenceBlock = (FireEssenceBlock)(new FireEssenceBlock().setRegistryName(Elementum.MODID, "fire_essence_block"));
        blockRegisterEvent.getRegistry().register(fireEssenceBlock);

        waterEssenceBlock = (WaterEssenceBlock)(new WaterEssenceBlock().setRegistryName(Elementum.MODID, "water_essence_block"));
        blockRegisterEvent.getRegistry().register(waterEssenceBlock);

        earthEssenceBlock = (EarthEssenceBlock)(new EarthEssenceBlock().setRegistryName(Elementum.MODID, "earth_essence_block"));
        blockRegisterEvent.getRegistry().register(earthEssenceBlock);
    }

    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
        // We need to create a BlockItem so the player can carry this block in their hand and it can appear in the inventory

        Item.Properties itemSimpleProperties = new Item.Properties()
                .tab(ModItemGroup.MAIN_ITEM_GROUP);  // which inventory tab?
        fireEssenceBlockItem = new BlockItem(fireEssenceBlock, itemSimpleProperties);
        fireEssenceBlockItem.setRegistryName(Objects.requireNonNull(fireEssenceBlock.getRegistryName()));
        itemRegisterEvent.getRegistry().register(fireEssenceBlockItem);

        waterEssenceBlockItem = new BlockItem(waterEssenceBlock, itemSimpleProperties);
        waterEssenceBlockItem.setRegistryName(Objects.requireNonNull(waterEssenceBlock.getRegistryName()));
        itemRegisterEvent.getRegistry().register(waterEssenceBlockItem);

        earthEssenceBlockItem = new BlockItem(earthEssenceBlock, itemSimpleProperties);
        earthEssenceBlockItem.setRegistryName(Objects.requireNonNull(earthEssenceBlock.getRegistryName()));
        itemRegisterEvent.getRegistry().register(earthEssenceBlockItem);
    }
}
