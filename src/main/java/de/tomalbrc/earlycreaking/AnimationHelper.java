package de.tomalbrc.earlycreaking;

import de.tomalbrc.bil.api.AnimatedHolder;
import de.tomalbrc.bil.api.Animator;
import de.tomalbrc.toms_mobs.entities.passive.Capybara;
import net.minecraft.world.entity.LivingEntity;

public class AnimationHelper {

    public static void updateWalkAnimation(LivingEntity entity, AnimatedHolder holder) {
        updateWalkAnimation(entity, holder, 0);
    }

    public static void updateWalkAnimation(LivingEntity entity, AnimatedHolder holder, int priority) {
        Animator animator = holder.getAnimator();
        if (entity.walkAnimation.isMoving() && entity.walkAnimation.speed() > 0.02) {
            animator.playAnimation("walk", priority);
            animator.pauseAnimation("idle");
        } else {
            animator.pauseAnimation("walk");
            animator.playAnimation("idle", priority, true);
        }
    }

    public static void updateHurtColor(LivingEntity entity, AnimatedHolder holder) {
        if (entity.hurtTime > 0 || entity.deathTime > 0)
            holder.setColor(0xff7e7e);
        else
            holder.clearColor();
    }
}