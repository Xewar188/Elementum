package items;

import entities.FireEssenceItemEntity;
import entities.WaterEssenceItemEntity;
import init.ModItemGroup;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterEssence extends Item{


    private final static int MAX_DURABILITY = 200;

    public WaterEssence() {

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


    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {

        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) {
            return ret;
        }
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            Direction direction = blockraytraceresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos1, direction, itemstack)) {
                BlockState blockstate = worldIn.getBlockState(blockpos);
                BlockPos blockpos2 = blockstate.getBlock() instanceof ILiquidContainer ? blockpos : blockraytraceresult.getBlockPos().offset
                        (new Vector3i(blockraytraceresult.getLocation().x(), blockraytraceresult.getLocation().y(), blockraytraceresult.getLocation().z()));

                if (((BucketItem) Items.WATER_BUCKET).emptyBucket(playerIn, worldIn, blockpos1, blockraytraceresult)) {
                    if (playerIn instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1, itemstack);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(handIn);
                    });
                    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
                } else {
                    return new ActionResult<>(ActionResultType.FAIL, itemstack);
                }
            } else {
                return new ActionResult<>(ActionResultType.FAIL, itemstack);
            }

        }
    }


    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        Entity toReturn = new WaterEssenceItemEntity(world, location.getX(), location.getY(), location.getZ(), itemstack);
        toReturn.setDeltaMovement(location.getDeltaMovement());
        return toReturn;
    }
}
