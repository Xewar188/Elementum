package global;

import init.ModBlocks;
import init.ModBlocksClientSide;
import init.ModItemsClientSide;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientSideOnlyModEventRegistrar {

    private final IEventBus eventBus;

    public ClientSideOnlyModEventRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * Register client only events. This method must only be called when it is certain that the mod is
     * is executing code on the client side and not the dedicated server.
     */
    public void registerClientOnlyEvents() {
        eventBus.register(ModBlocksClientSide.class);
        eventBus.register(ModItemsClientSide.class);
    }
}
