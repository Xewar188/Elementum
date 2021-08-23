package entities;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FireEssenceItemEntity extends ItemEntity {

    int FIRE_GENERATION_RATE = 10;
    int CURRENT_FIRE_COUNT = 10;
    public FireEssenceItemEntity(World p_i1710_1_, double p_i1710_2_, double p_i1710_4_, double p_i1710_6_, ItemStack p_i1710_8_) {
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
        if(CURRENT_FIRE_COUNT == FIRE_GENERATION_RATE) {
            CURRENT_FIRE_COUNT = 0;
            generateFire();
        }
        else {
            CURRENT_FIRE_COUNT++;
        }

    }


    public void generateFire() {
        if (!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            BlockPos blockpos = this.blockPosition();

            for(int i = 0; i < this.random.nextInt(3)+1; ++i) {
                BlockPos blockpos1 = blockpos.offset(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
                if (this.blockPosition().getX() == blockpos1.getX() && this.blockPosition().getZ() == blockpos1.getZ())
                    continue;
                BlockState blockstate = AbstractFireBlock.getState(this.level, blockpos1);
                if (this.level.getBlockState(blockpos1).is(Blocks.AIR) && blockstate.canSurvive(this.level, blockpos1)) {
                    this.level.setBlockAndUpdate(blockpos1, blockstate);
                }
            }

        }
    }

}
