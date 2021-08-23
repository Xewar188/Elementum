package init;

import items.EarthEssence;
import items.FireEssence;
import items.WaterEssence;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModItems {
    public static FireEssence fireEssence;
    public static WaterEssence waterEssence;
    public static EarthEssence earthEssence;

    @SubscribeEvent
    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
        fireEssence = new FireEssence();
        fireEssence.setRegistryName("fire_essence");
        itemRegisterEvent.getRegistry().register(fireEssence);

        waterEssence = new WaterEssence();
        waterEssence.setRegistryName("water_essence");
        itemRegisterEvent.getRegistry().register(waterEssence);

        earthEssence = new EarthEssence();
        earthEssence.setRegistryName("earth_essence");
        itemRegisterEvent.getRegistry().register(earthEssence);
    }

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
    }
}
