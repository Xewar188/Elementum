package blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class WaterEssenceBlock extends Block {

    public WaterEssenceBlock() {
        super(Block.Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .strength(3.0F,3.0F));

    }

}
