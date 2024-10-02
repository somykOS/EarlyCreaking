package de.tomalbrc.earlycreaking;

import de.tomalbrc.earlycreaking.registries.BlockRegistry;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

public class CreakingHeartBlock extends RotatedPillarBlock implements EntityBlock, PolymerTexturedBlock {
    private final Map<Direction.Axis, BlockState> stateMap;
    private final Map<Direction.Axis, BlockState> stateMapLit;

    public CreakingHeartBlock(Properties properties) {
        super(properties);

        this.stateMap = new IdentityHashMap<>();
        this.stateMapLit = new IdentityHashMap<>();
        this.stateMap.put(Direction.Axis.X, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart"), 90, 90)));
        this.stateMap.put(Direction.Axis.Y, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart"), 0, 0)));
        this.stateMap.put(Direction.Axis.Z, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart"), 90, 0)));

        this.stateMapLit.put(Direction.Axis.Y, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart_lit"), 0, 0)));
        this.stateMapLit.put(Direction.Axis.Z, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart_lit"), 90, 0)));
        this.stateMapLit.put(Direction.Axis.X, PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(ResourceLocation.fromNamespaceAndPath("minecraft", "block/creakingheart_lit"), 90, 90)));

        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.LIT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        var bs = super.getStateForPlacement(blockPlaceContext);
        BlockState state = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(bs.getValue(AXIS), 1));
        BlockState state2 = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(bs.getValue(AXIS), -1));

        if (state2 == state && state.hasProperty(AXIS) && state.getValue(AXIS) == bs.getValue(AXIS)) {
            bs = bs.setValue(BlockStateProperties.LIT, true);
        }

        return bs;
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean wasLit = blockState.getValue(BlockStateProperties.LIT);
        BlockState state = level.getBlockState(blockPos.relative(blockState.getValue(AXIS), 1));
        BlockState state2 = level.getBlockState(blockPos.relative(blockState.getValue(AXIS), -1));

        if (!wasLit && state2 == state && state.hasProperty(AXIS) && state.getValue(AXIS) == blockState.getValue(AXIS)) {
            level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, true), 2);
        } else if (wasLit) {
            level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, false), 2);
        }

        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockRegistry.CREAKING_HEART_BLOCK_ENTITY.create(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == BlockRegistry.CREAKING_HEART_BLOCK_ENTITY ? CreakingHeartBlockEntity::tick : null;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState blockState) {
        return blockState.getValue(BlockStateProperties.LIT) ?
                this.stateMapLit.get(blockState.getValue(AXIS)) :
                this.stateMap.get(blockState.getValue(AXIS));
    }
}
