package init;

import blocks.AirEssenceBlock;
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

    public static AirEssenceBlock airEssenceBlock;
    public static BlockItem airEssenceBlockItem;

    @SubscribeEvent
    public static void onBlocksRegistration(final RegistryEvent.Register<Block> blockRegisterEvent) {
        fireEssenceBlock = (FireEssenceBlock) registerBlock(new FireEssenceBlock(),"fire_essence_block", blockRegisterEvent);

        waterEssenceBlock = (WaterEssenceBlock)registerBlock(new WaterEssenceBlock(),"water_essence_block", blockRegisterEvent);

        earthEssenceBlock = (EarthEssenceBlock)registerBlock(new EarthEssenceBlock(),"earth_essence_block", blockRegisterEvent);

        airEssenceBlock = (AirEssenceBlock)registerBlock(new AirEssenceBlock(),"air_essence_block", blockRegisterEvent);
    }

    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
        // We need to create a BlockItem so the player can carry this block in their hand and it can appear in the inventory

        Item.Properties itemSimpleProperties = new Item.Properties()
                .tab(ModItemGroup.MAIN_ITEM_GROUP);  // which inventory tab?

        fireEssenceBlockItem = registerItem(fireEssenceBlock, itemSimpleProperties, itemRegisterEvent);

        waterEssenceBlockItem = registerItem(waterEssenceBlock, itemSimpleProperties, itemRegisterEvent);

        earthEssenceBlockItem = registerItem(earthEssenceBlock, itemSimpleProperties, itemRegisterEvent);

        airEssenceBlockItem = registerItem(airEssenceBlock, itemSimpleProperties, itemRegisterEvent);
    }

    public static Block registerBlock(Block toRegister, String itemName, final RegistryEvent.Register<Block> blockRegisterEvent) {
        Block toReturn = toRegister.setRegistryName(Elementum.MODID, itemName);
        blockRegisterEvent.getRegistry().register(toReturn);
        return toReturn;
    }

    public static BlockItem registerItem(Block toRegister, Item.Properties itemSimpleProperties, final RegistryEvent.Register<Item> itemRegisterEvent) {
        BlockItem toReturn = new BlockItem(toRegister, itemSimpleProperties);
        toReturn.setRegistryName(Objects.requireNonNull(toRegister.getRegistryName()));
        itemRegisterEvent.getRegistry().register(toReturn);
        return toReturn;
    }
}
