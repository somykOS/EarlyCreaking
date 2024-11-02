package de.tomalbrc.earlycreaking;

import de.tomalbrc.earlycreaking.registries.BlockRegistry;
import de.tomalbrc.earlycreaking.registries.MobRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LargeEntitySpawnHelper;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreakingHeartBlockEntity extends BlockEntity {
    private Creaking entity;

    public CreakingHeartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockRegistry.CREAKING_HEART_BLOCK_ENTITY, blockPos, blockState);

    }

    public void spawnMob() {
        if (this.world != null && this.entity == null) {
            var spawnedMob = LargeEntitySpawnHelper.trySpawnAt(MobRegistry.CREAKING, SpawnReason.TRIGGERED, (ServerWorld) world, this.getPos(), 20, 5, 6, LargeEntitySpawnHelper.Requirements.WARDEN);
            spawnedMob.ifPresent(creaking -> this.entity = creaking);
        }
    }

    @Override
    public void markRemoved() {
        this.removeMob();
        super.markRemoved();
    }

    public void removeMob() {
        if (this.entity != null) {
            this.entity.discard();
            this.entity = null;
        }
    }

    public static boolean isWorldNaturalAndNight(World world) {
        return world.getDimension().natural() && world.isNight();
    }

    public static <T extends BlockEntity> void tick(World level, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof CreakingHeartBlockEntity creakingHeartBlockEntity) {
            if (blockState.get(Properties.LIT) && isWorldNaturalAndNight(level)) creakingHeartBlockEntity.spawnMob();
            else if (creakingHeartBlockEntity.entity != null && !creakingHeartBlockEntity.entity.isRemoved()) creakingHeartBlockEntity.removeMob();
        }
    }
}
