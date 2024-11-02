package de.tomalbrc.earlycreaking;

import de.tomalbrc.earlycreaking.registries.BlockRegistry;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CreakingHeartBlock extends PillarBlock implements BlockEntityProvider, PolymerTexturedBlock {
    private final Map<Direction.Axis, BlockState> stateMap;
    private final Map<Direction.Axis, BlockState> stateMapLit;

    public CreakingHeartBlock(Settings properties) {
        super(properties);

        this.stateMap = new IdentityHashMap<>();
        this.stateMapLit = new IdentityHashMap<>();
        this.stateMap.put(Direction.Axis.X, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("minecraft", "block/creakingheart"), 90, 90)));
        this.stateMap.put(Direction.Axis.Y, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("minecraft", "block/creakingheart"), 0, 0)));
        this.stateMap.put(Direction.Axis.Z, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("minecraft", "block/creakingheart"), 90, 0)));

        this.stateMapLit.put(Direction.Axis.Y, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("minecraft", "block/creakingheart_lit"), 0, 0)));
        this.stateMapLit.put(Direction.Axis.Z, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("minecraft", "block/creakingheart_lit"), 90, 0)));
        this.stateMapLit.put(Direction.Axis.X, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(Identifier.of("minecraft", "block/creakingheart_lit"), 90, 90)));

        this.setDefaultState(this.getDefaultState().with(Properties.LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.LIT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext blockPlaceContext) {
        var bs = super.getPlacementState(blockPlaceContext);
        BlockState state = blockPlaceContext.getWorld().getBlockState(blockPlaceContext.getBlockPos().offset(bs.get(AXIS), 1));
        BlockState state2 = blockPlaceContext.getWorld().getBlockState(blockPlaceContext.getBlockPos().offset(bs.get(AXIS), -1));

        if (state2 == state && state.contains(AXIS) && state.get(AXIS) == bs.get(AXIS)) {
            bs = bs.with(Properties.LIT, true);
        }

        return bs;
    }

    @Override
    public void neighborUpdate(BlockState blockState, World level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean wasLit = blockState.get(Properties.LIT);
        BlockState state = level.getBlockState(blockPos.offset(blockState.get(AXIS), 1));
        BlockState state2 = level.getBlockState(blockPos.offset(blockState.get(AXIS), -1));

        if (!wasLit && state2 == state && state.contains(AXIS) && state.get(AXIS) == blockState.get(AXIS)) {
            level.setBlockState(blockPos, blockState.with(Properties.LIT, true), 2);
        } else if (wasLit) {
            level.setBlockState(blockPos, blockState.with(Properties.LIT, false), 2);
        }

        super.neighborUpdate(blockState, level, blockPos, block, blockPos2, bl);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockRegistry.CREAKING_HEART_BLOCK_ENTITY.instantiate(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World level, BlockState state, BlockEntityType<T> type) {
        return type == BlockRegistry.CREAKING_HEART_BLOCK_ENTITY ? CreakingHeartBlockEntity::tick : null;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState blockState) {
        return blockState.get(Properties.LIT) ?
                this.stateMapLit.get(blockState.get(AXIS)) :
                this.stateMap.get(blockState.get(AXIS));
    }
}
