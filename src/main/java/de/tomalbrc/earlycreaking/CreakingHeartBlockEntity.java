package de.tomalbrc.earlycreaking;

import de.tomalbrc.earlycreaking.registries.BlockRegistry;
import de.tomalbrc.earlycreaking.registries.MobRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class CreakingHeartBlockEntity extends BlockEntity {
    private Creaking entity;

    public CreakingHeartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockRegistry.CREAKING_HEART_BLOCK_ENTITY, blockPos, blockState);

    }

    public void spawnMob() {
        if (this.level != null && this.entity == null) {
            var spawnedMob = SpawnUtil.trySpawnMob(MobRegistry.CREAKING, MobSpawnType.TRIGGERED, (ServerLevel) level, this.getBlockPos(), 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER);
            spawnedMob.ifPresent(creaking -> {
                this.entity = creaking;
            });
        }
    }

    @Override
    public void setRemoved() {
        this.removeMob();
        super.setRemoved();
    }

    public void removeMob() {
        if (this.entity != null) {
            this.entity.discard();
            this.entity = null;
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof CreakingHeartBlockEntity creakingHeartBlockEntity) {
            if (blockState.getValue(BlockStateProperties.LIT)) creakingHeartBlockEntity.spawnMob();
            else if (creakingHeartBlockEntity.entity != null && !creakingHeartBlockEntity.entity.isRemoved()) creakingHeartBlockEntity.removeMob();
        }
    }
}
