package blocks;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class EarthEssenceBlock extends Block {

    public EarthEssenceBlock() {
        super(Block.Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .strength(3.0F,1200.0F));

    }

}
