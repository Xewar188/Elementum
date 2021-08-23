package init;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModBlocksClientSide {
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.fireEssenceBlock, RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.waterEssenceBlock, RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModBlocks.earthEssenceBlock, RenderType.solid());
    }
}
