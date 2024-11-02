package de.tomalbrc.earlycreaking;

import de.tomalbrc.bil.api.AnimatedEntity;
import de.tomalbrc.bil.core.holder.entity.EntityHolder;
import de.tomalbrc.bil.core.holder.entity.living.LivingEntityHolder;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.earlycreaking.registries.ItemRegistry;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Creaking extends HostileEntity implements AnimatedEntity {
    public static final Identifier ID = Identifier.of("minecraft","creaking");
    public static final Model MODEL = Util.loadBbModel(ID);
    private final EntityHolder<Creaking> holder;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1000.0)
                .add(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE, 1000.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4);
    }

    @Override
    public EntityHolder<Creaking> getHolder() {
        return this.holder;
    }

    public Creaking(EntityType<? extends Creaking> type, World level) {
        super(type, level);

        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DOOR_IRON_CLOSED, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DOOR_WOOD_CLOSED, -1.0F);

        this.holder = new LivingEntityHolder<>(this, MODEL);
        EntityAttachment.ofTicking(this.holder, this);

        setPersistent();
    }

    @Override
    public float getShadowRadius() {
        return 0;
    }

    @Override
    public boolean canSpawn(WorldAccess levelAccessor, SpawnReason mobSpawnType) {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return !damageSource.isOf(DamageTypes.GENERIC_KILL);
    }

    @Override
    public boolean collidesWith(Entity entity) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new CreakingFreezeWhenLookedAt(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age % 2 == 0) {
            AnimationHelper.updateWalkAnimation(this, this.holder);
            AnimationHelper.updateHurtColor(this, this.holder);
        }
    }

    @Override
    public void remove(RemovalReason removalReason) {
        if (this.getWorld() instanceof ServerWorld serverLevel) {
            serverLevel.spawnParticles(ParticleTypes.POOF, getX(), getY()+1, getZ(),80,0.2, 0.8, 0.2, 0);
        }
        if (Math.random() < 0.1) {
            this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.prevX, this.prevY, this.prevZ, ItemRegistry.WOODEN_HEART.getDefaultStack()));
        }
        super.remove(removalReason);
    }

    @Override
    public void mobTick() {
        super.mobTick();

    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 220;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_FROG_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    private static class CreakingFreezeWhenLookedAt extends Goal {
        private final Creaking creaking;
        @Nullable
        private LivingEntity target;

        public CreakingFreezeWhenLookedAt(Creaking creaking) {
            this.creaking = creaking;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        public boolean canStart() {
            this.target = this.creaking.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            } else {
                double distancedToSqr = this.target.squaredDistanceTo(this.creaking);
                return !(distancedToSqr > 1024) && (distancedToSqr < 1 || !isInFOV());
            }
        }

        private static final double fov = 90 * MathHelper.RADIANS_PER_DEGREE;
        private boolean isInFOV() {
            Vec3d directionFacing = this.target.getRotationVec(1).normalize();
            Vec3d directionToTarget = this.target.getPos().subtract(this.creaking.getPos()).normalize();
            return Math.acos(directionFacing.dotProduct(directionToTarget)) <= fov;
        }

        public void start() {
            this.creaking.getNavigation().stop();
        }
    }
}