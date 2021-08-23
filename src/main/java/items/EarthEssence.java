package items;

import entities.EarthEssenceItemEntity;
import init.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

public class EarthEssence extends Item{


    private final static Block[] affectedBlocks = {Blocks.STONE,Blocks.DIRT,Blocks.GRASS_BLOCK,Blocks.SAND,Blocks.GRAVEL};
    private final static int MAX_DURABILITY = 200;

    public EarthEssence() {

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
    public ActionResultType useOn(ItemUseContext target) {
        int maximumDepth = 3;
        int surfaceOffset = 0;
        int freeSpaceHeight = 0;
        BlockPos curCheckedPos = target.getClickedPos();

        if (!isAcceptedBlock(target.getLevel().getBlockState(curCheckedPos))) {
            return super.useOn(target);
        }
        curCheckedPos = curCheckedPos.offset(0,1,0);

        for (int i = 0; i < 2 * maximumDepth - 1; i++) {
            BlockState toCheck = target.getLevel().getBlockState(curCheckedPos);
            if (isAcceptedBlock(toCheck)) {
                surfaceOffset++;
                if (surfaceOffset > maximumDepth)
                    return super.useOn(target);
            }
            else {
                break;
            }
            curCheckedPos = curCheckedPos.offset(0,1,0);
        }

        for (int i = surfaceOffset; i < 2 * maximumDepth - 1; i++) {
            BlockState toCheck = target.getLevel().getBlockState(curCheckedPos);
            if (toCheck.getMaterial().isReplaceable() || toCheck.isAir()) {
                freeSpaceHeight++;
                if (freeSpaceHeight == maximumDepth)
                    break;
            }
            else {
                break;
            }
            curCheckedPos = curCheckedPos.offset(0,1,0);
        }

        BlockState[] toMove = new BlockState[surfaceOffset + 1];
        curCheckedPos = target.getClickedPos().offset(0,surfaceOffset,0);
        for (int i = 0; i < surfaceOffset + 1; i++) {
            toMove[i] = target.getLevel().getBlockState(curCheckedPos);
            target.getLevel().setBlock(curCheckedPos,Blocks.AIR.defaultBlockState(), 0);
            curCheckedPos = curCheckedPos.offset(0,-1,0);
        }

        curCheckedPos = target.getClickedPos().offset(0,surfaceOffset,0);
        for (int i = 0; i < surfaceOffset + 1; i++) {
            curCheckedPos = curCheckedPos.offset(0,1,0);
            target.getLevel().destroyBlock(curCheckedPos,true);
            target.getLevel().setBlockAndUpdate(curCheckedPos,toMove[surfaceOffset - i]);
            target.getLevel().updateNeighborsAt(curCheckedPos, Blocks.DIRT);
        }
        Collection<Entity> toMoveEntities = target.getLevel().getEntities((Entity) null,
                new AxisAlignedBB(target.getClickedPos().getX() - 0.25, target.getClickedPos().getY() - 1.25, target.getClickedPos().getZ() - 0.25,
                        target.getClickedPos().getX() + 1.25, target.getClickedPos().getY() + 2.25, target.getClickedPos().getZ() + 1.25 ),
                (a) -> true);
        for(Entity cur : toMoveEntities) {
            cur.setPos(cur.getX(),cur.blockPosition().getY() + surfaceOffset + 1, cur.getZ());
        }

        Objects.requireNonNull(target.getPlayer()).getItemInHand(target.getHand()).hurtAndBreak
                (surfaceOffset + 1, target.getPlayer(), (p_220040_1_) -> p_220040_1_.broadcastBreakEvent(target.getHand()));
        return super.useOn(target);
    }

    private boolean isAcceptedBlock(BlockState toCheck) {
        boolean isCorrect = false;
        for (Block cur : affectedBlocks) {
            if (toCheck.is(cur)) {
                System.out.println(cur);
                System.out.println(toCheck);
                isCorrect = true;
                break;
            }
        }
        return isCorrect;
    }
    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        Entity toReturn = new EarthEssenceItemEntity(world, location.getX(), location.getY(), location.getZ(), itemstack);
        toReturn.setDeltaMovement(location.getDeltaMovement());
        return toReturn;
    }
}
