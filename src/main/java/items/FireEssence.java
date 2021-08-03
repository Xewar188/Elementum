package items;

import entities.FireEssenceItemEntity;
import init.ModItemGroup;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FireEssence extends Item{

    private final static int FLAME_RANGE = 5;
    private final static int MAX_DURABILITY = 200;

    public FireEssence() {

        super(new Item.Properties().tab(ModItemGroup.MAIN_ITEM_GROUP).defaultDurability(MAX_DURABILITY).durability(MAX_DURABILITY));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {

        int heightMod = 0;
        if (playerIn.getItemInHand(hand).getDamageValue() >= MAX_DURABILITY)
            return new ActionResult(ActionResultType.FAIL,  playerIn.getItemInHand(hand));
        for (int i = 1; i <= FLAME_RANGE; i++) {
            if (!worldIn.isClientSide && worldIn.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
                double playerDirSin = -Math.sin(playerIn.getRotationVector().y * Math.PI * 2 / 360D );
                double playerDirCos = Math.cos(playerIn.getRotationVector().y * Math.PI * 2 / 360D );


                BlockPos[] blockpos = new BlockPos[3];
                for (int j = -1; j< 2; j++) {
                    blockpos[j+1] = playerIn.blockPosition().offset(Math.round(i * playerDirSin) ,
                            j + heightMod,
                            Math.round(i * playerDirCos));

                }

                if (blockpos[1].closerThan(playerIn.position(),1))
                    continue;

                boolean success = false;
                for (int j = -1; j< 2; j++) {
                    if (worldIn.getBlockState(blockpos[j+1]).isAir() && AbstractFireBlock.getState(worldIn, blockpos[j+1]).canSurvive(worldIn, blockpos[j+1])) {
                        worldIn.setBlockAndUpdate(blockpos[j+1], AbstractFireBlock.getState(worldIn, blockpos[j+1]));
                        heightMod += j;
                        success = true;
                        break;
                    }

                    if (worldIn.getBlockState(blockpos[j+1]).getBlock() instanceof FireBlock) {
                        success = true;
                        heightMod += j;
                        break;
                    }
                }
                if (!success)
                    break;


            }
        }

        playerIn.getItemInHand(hand).hurtAndBreak(1, playerIn, (p_220040_1_) -> {
            p_220040_1_.broadcastBreakEvent(hand);
        });
        return new ActionResult(ActionResultType.PASS,  playerIn.getItemInHand(hand));
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        Entity toReturn = new FireEssenceItemEntity(world, location.getX(), location.getY(), location.getZ(), itemstack);
        toReturn.setDeltaMovement(location.getDeltaMovement());
        return toReturn;
    }
}
