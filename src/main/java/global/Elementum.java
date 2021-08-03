package global;

import init.ModBlocks;
import init.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Elementum.MODID)
public class Elementum {
    public static final String MODID = "elementum";

    public Elementum() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        final ClientSideOnlyModEventRegistrar clientSideOnlyModEventRegistrar = new ClientSideOnlyModEventRegistrar(modEventBus);

        registerCommonEvents(modEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientSideOnlyModEventRegistrar::registerClientOnlyEvents);
    }

    public void registerCommonEvents(IEventBus eventBus) {
        eventBus.register(ModItems.class);
        eventBus.register(ModBlocks.class);
    }
}
