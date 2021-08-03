package init;

import global.Elementum;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public final class ModItemGroup {

    private static Object elementum;
    public static final ItemGroup MAIN_ITEM_GROUP = new MainItemGroup(Elementum.MODID, () -> new ItemStack(ModBlocks.fireEssenceBlockItem));

    public static final class MainItemGroup extends ItemGroup {

        @Nonnull
        private final Supplier<ItemStack> iconSupplier;

        public MainItemGroup(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
            super("main_item_group");
            this.iconSupplier = iconSupplier;
        }

        @Override
        @Nonnull
        public ItemStack makeIcon() {
            return iconSupplier.get();
        }

    }

}