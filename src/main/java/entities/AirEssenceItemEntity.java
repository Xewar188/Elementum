package entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Collection;

public class AirEssenceItemEntity extends ItemEntity {

    int PUSH_RANGE = 10;
    public AirEssenceItemEntity(World p_i1710_1_, double p_i1710_2_, double p_i1710_4_, double p_i1710_6_, ItemStack p_i1710_8_) {
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
        Collection<Entity> toMoveEntities = this.level.getEntities((Entity) null,
                new AxisAlignedBB(this.getX() - PUSH_RANGE,this.getY() - PUSH_RANGE,this.getZ() - PUSH_RANGE,
                        this.getX() + PUSH_RANGE, this.getY() + PUSH_RANGE, this.getZ() + PUSH_RANGE),
                (Entity entity) -> (entity.distanceTo(this)<= PUSH_RANGE && !this.equals(entity)));
        for (Entity e : toMoveEntities) {
            float dis = e.distanceTo(this);
            float disx = (float) (e.getX() - this.getX());
            float disy = (float) (e.getY() - this.getY());
            float disz = (float) (e.getZ() - this.getZ());
            e.setDeltaMovement(e.getDeltaMovement().add( disx / dis * Math.sqrt(PUSH_RANGE - dis) / 80,
                    disy / dis * Math.sqrt(PUSH_RANGE - dis) / 80,
                    disz / dis * Math.sqrt(PUSH_RANGE - dis) / 80));
            if (e instanceof PlayerEntity) {
                e.hurtMarked = true;
            }
        }
    }



}

