package items;

import entities.AirEssenceItemEntity;
import init.ModItemGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;

public class AirEssence extends Item {

    private final static int PUSH_RANGE = 5;
    private final static int MAX_DURABILITY = 200;

    public AirEssence() {

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
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        Collection<Entity> toMoveEntities = playerIn.level.getEntities((Entity) null,
            new AxisAlignedBB(playerIn.getX() - PUSH_RANGE,playerIn.getY() - PUSH_RANGE,playerIn.getZ() - PUSH_RANGE,
                    playerIn.getX() + PUSH_RANGE, playerIn.getY() + PUSH_RANGE, playerIn.getZ() + PUSH_RANGE),
            (Entity entity) -> (entity.distanceTo(playerIn)<= PUSH_RANGE && !playerIn.equals(entity)));
        for (Entity e : toMoveEntities) {
            float dis = e.distanceTo(playerIn);
            float disx = (float) (e.getX() - playerIn.getX());
            float disy = (float) (e.getY() - playerIn.getY());
            float disz = (float) (e.getZ() - playerIn.getZ());
            e.setDeltaMovement(e.getDeltaMovement().add( disx / dis * Math.sqrt(PUSH_RANGE - dis) / 5,
                    disy / dis * Math.sqrt(PUSH_RANGE - dis) / 5,
                    disz / dis * Math.sqrt(PUSH_RANGE - dis) / 5));
            if (e instanceof PlayerEntity) {
                e.hurtMarked = true;
            }
        }
        if(!toMoveEntities.isEmpty())
            playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(handIn);
            });
        return super.use(worldIn, playerIn, handIn);
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        Entity toReturn = new AirEssenceItemEntity(world, location.getX(), location.getY(), location.getZ(), itemstack);
        toReturn.setDeltaMovement(location.getDeltaMovement());
        return toReturn;
    }
}
