package de.tomalbrc.earlycreaking;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.resourcepack.BaseItemProvider;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class ResinJarBlock extends LanternBlock implements FactoryBlock, PolymerTexturedBlock {
    private final ItemStack model;

    public ResinJarBlock(Settings settings, Identifier modelPath) {
        super(settings);
        model = BaseItemProvider.requestModel(modelPath);
    }

    @Override
    public ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(model);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return PolymerBlockResourceUtils.requestEmpty(BlockModelType.PLANT_BLOCK);
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, ServerPlayerEntity player) {
        return Blocks.GLASS.getDefaultState();
    }

    private static final class Model extends BlockModel {

        private Model(ItemStack model) {
            ItemDisplayElement mainElement = ItemDisplayElementUtil.createSimple(model);
            mainElement.setScale(new Vector3f(1f, 1f, 1.7f));
            mainElement.setOffset(new Vec3d(0f, -0.16f, -0.13f));
            this.addElement(mainElement);
        }
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction[] var3 = ctx.getPlacementDirections();

        for (Direction direction : var3) {
            if (direction.getAxis() != Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.DOWN);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState.with(LanternBlock.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

}
