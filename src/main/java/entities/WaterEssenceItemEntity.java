package entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WaterEssenceItemEntity extends ItemEntity {

    World placedWorld = null;
    BlockPos placedWater = null;
    public WaterEssenceItemEntity(World p_i1710_1_, double p_i1710_2_, double p_i1710_4_, double p_i1710_6_, ItemStack p_i1710_8_) {
        super(p_i1710_1_, p_i1710_2_, p_i1710_4_, p_i1710_6_, p_i1710_8_);

        this.setPickUpDelay(20);
    }

    @Override
    public boolean hasPickUpDelay() {
        return true;
    }


    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnGround()) {
            if (placedWater != null) {
                placedWorld.setBlock(placedWater, Blocks.AIR.defaultBlockState(), 11);
                placedWater = null;
            }
            if (this.level.getBlockState(this.blockPosition()).canBeReplaced(Fluids.WATER)) {
                placedWater = this.blockPosition();
                placedWorld = this.level;
                this.level.destroyBlock(this.blockPosition(), true);
                this.level.setBlock(this.blockPosition(), Fluids.WATER.defaultFluidState().createLegacyBlock(), 11);
            }
        }
    }

    @Override
    public void remove() {
        if (placedWater != null)
            placedWorld.setBlock(placedWater, Blocks.AIR.defaultBlockState(), 11);
        super.remove();
    }


    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isUnderWater() {
        return false;
    }
}
