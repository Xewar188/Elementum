package blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class EarthEssenceBlock extends Block {

    public EarthEssenceBlock() {
        super(Block.Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .strength(6.0F,1200.0F));

    }

}
