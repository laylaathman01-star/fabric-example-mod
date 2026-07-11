package com.example;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {

    public ClickGuiScreen() {
        // Gibt dem Bildschirm einen internen Namen
        super(Text.literal("Karzan ClickGUI"));
    }

    // 1. Das Aussehen: Hier wird das Menü gezeichnet
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Zeichnet den normalen, leicht abgedunkelten Minecraft-Hintergrund
        this.renderBackground(context, mouseX, mouseY, delta);

        // Position des Menü-Fensters (Mitte des Bildschirms)
        int x = 50;
        int y = 50;
        int width = 150;
        int height = 100;

        // Hauptfenster zeichnen (Dunkelgrau mit rotem Rand für Karzan)
        context.fill(x, y, x + width, y + height, 0xDD202020); // Hintergrund (Hex-Code für ARGB)
        context.drawBorder(context, x, y, width, height, 0xFFFF0000); // Roter Rahmen

        // Titel des Menüs oben links im Kasten hinschreiben
        context.drawText(this.textRenderer, "§cKarzan §fClient", x + 10, y + 10, 0xFFFFFF, false);

        // Knopf für "Sus Chunks" zeichnen
        int buttonX = x + 10;
        int buttonY = y + 35;
        int buttonW = width - 20;
        int buttonH = 20;

        // Farbe des Knopfes ändern: Grün wenn AN, Grau/Rot wenn AUS
        int buttonColor = SusChunks.enabled ? 0xFF22AA22 : 0xFF444444;
        context.fill(buttonX, buttonY, buttonX + buttonW, buttonY + buttonH, buttonColor);
        context.drawBorder(context, buttonX, buttonY, buttonW, buttonH, 0xFF888888);

        // Text auf dem Knopf anzeigen
        String statusText = "Sus Chunks: " + (SusChunks.enabled ? "§aON" : "§cOFF");
        context.drawText(this.textRenderer, statusText, buttonX + 10, buttonY + 6, 0xFFFFFF, false);

        super.render(context, mouseX, mouseY, delta);
    }

    // 2. Die Funktion: Was passiert bei einem Mausklick?
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = 50;
        int y = 50;
        int buttonX = x + 10;
        int buttonY = y + 35;
        int buttonW = 150 - 20;
        int buttonH = 20;

        // Überprüfen, ob die Maus genau über dem "Sus Chunks"-Knopf steht
        if (mouseX >= buttonX && mouseX <= buttonX + buttonW && mouseY >= buttonY && mouseY <= buttonY + buttonH) {
            // Zustand umdrehen (An -> Aus / Aus -> An)
            SusChunks.enabled = !SusChunks.enabled;
            
            // Ein Klick-Geräusch abspielen, damit es sich echt anfühlt
            if (this.client != null && this.client.player != null) {
                this.client.player.playSound(net.minecraft.sound.SoundEvents.UI_BUTTON_CLICK.value(), 1.0f, 1.0f);
            }
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    // Das Spiel wird im Hintergrund nicht pausiert, wenn das Menü offen ist (wichtig für Multiplayer)
    @Override
    public boolean shouldPause() {
        return false;
    }
}
