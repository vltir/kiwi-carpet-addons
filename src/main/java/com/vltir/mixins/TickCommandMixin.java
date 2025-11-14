package com.vltir.mixins;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.vltir.KiwiSettings;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.TickCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TickCommand.class)
public abstract class TickCommandMixin {
    @Definition(id = "getTickManager", method = "Lnet/minecraft/server/MinecraftServer;getTickManager()Lnet/minecraft/server/ServerTickManager;")
    @Expression("? = ?.getTickManager()")
    @ModifyVariable(
            method = "executeFreeze",
            at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER),
            argsOnly = true
    )
    private static boolean injected(boolean freeze, @Local ServerTickManager serverTickManager) {
        return freeze && (!KiwiSettings.tickFreezeToggle || !serverTickManager.isFrozen());
    }
}
