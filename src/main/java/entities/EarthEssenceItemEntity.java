package entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class EarthEssenceItemEntity extends ItemEntity {

    int currentTick = 0;
    int howOftenCreateDirt = 25;
    public EarthEssenceItemEntity(World p_i1710_1_, double p_i1710_2_, double p_i1710_4_, double p_i1710_6_, ItemStack p_i1710_8_) {
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
        currentTick++;
        if (howOftenCreateDirt != currentTick) {
            return;
        }

        currentTick = 0;
        if (this.isOnGround() && Math.abs(this.getDeltaMovement().x) < 0.01 &&
                Math.abs(this.getDeltaMovement().z) < 0.01) {
            Point[] directions = {new Point(1,0),new Point(-1,0),new Point(0,-1),new Point(0,1)};
            Collections.shuffle(Arrays.asList(directions));

            boolean hasPlacedBlock = false;
            for (Point currentDir : directions) {
                BlockPos currentPyrTop = this.blockPosition().offset(currentDir.x ,0, currentDir.y);
                if (tryPlacingPyramid(currentPyrTop)) {
                    hasPlacedBlock = true;
                    break;
                }
            }
            if (hasPlacedBlock)
                return;

            BlockPos blockAboveEntity = this.blockPosition().offset(0,1,0);
            if (this.level.getBlockState(blockAboveEntity).is(Blocks.AIR)) {
                this.level.setBlockAndUpdate(blockAboveEntity, Blocks.DIRT.defaultBlockState());
                this.level.updateNeighborsAt(blockAboveEntity, Blocks.DIRT);
            }
        }
    }


    //returns if block was placed
    public boolean tryPlacingPyramid(BlockPos positionOfTop) {
        if (!this.level.getBlockState(positionOfTop).is(Blocks.AIR)) {
            return false;
        }
        BlockPos mainPyrTop = positionOfTop.offset(0,-1,0);
        if (tryPlacingPyramid(mainPyrTop))
            return true;

        Point[] directions = {new Point(1,0),new Point(-1,0),new Point(0,-1),new Point(0,1)};
        Collections.shuffle(Arrays.asList(directions));

        for (Point currentDir : directions) {
            BlockPos currentPyrTop = mainPyrTop.offset(currentDir.x ,0, currentDir.y);
            if (tryPlacingPyramid(currentPyrTop))
                return true;
        }
        this.level.setBlockAndUpdate(positionOfTop, Blocks.DIRT.defaultBlockState());
        this.level.updateNeighborsAt(positionOfTop, Blocks.DIRT);
        return true;
    }

}
