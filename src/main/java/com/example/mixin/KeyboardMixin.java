package com.example.mixin;

import com.example.ClickGuiScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        // action == 1 bedeutet: Eine Taste wurde gedrückt
        if (action == 1) { 
            MinecraftClient mc = MinecraftClient.getInstance();
            
            if (mc.player != null) {
                // Wenn die "Rechte Shift-Taste" gedrückt wird
                if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                    
                    // Wenn noch kein anderes Menü offen ist, öffne unsere ClickGUI
                    if (mc.currentScreen == null) {
                        mc.execute(() -> mc.setScreen(new ClickGuiScreen()));
                    }
                }
            }
        }
    }
}
