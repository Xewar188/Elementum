package blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class AirEssenceBlock extends Block {

    public AirEssenceBlock() {
        super(Block.Properties.of(Material.LEAVES)
                .strength(0.5F,2.0F));

    }

}