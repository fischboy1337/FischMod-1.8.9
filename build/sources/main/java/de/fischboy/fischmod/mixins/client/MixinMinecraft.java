package de.fischboy.fischmod.mixins.client;

import com.google.gson.JsonObject;
import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;
import de.fischboy.fischmod.api.utils.server.PlayerAPI;
import de.fischboy.fischmod.management.event.impl.GameTickEvent;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    public abstract boolean isSingleplayer();

    @Shadow
    public abstract ServerData getCurrentServerData();

    @Setter
    @Shadow
    private boolean running;

    private final PlayerAPI playerAPI = new PlayerAPI();

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo info) {
        FischMod.INSTANCE.start();
        Display.setTitle(FischMod.NAME + " " + FischMod.VERSION + " | " + Display.getTitle());
    }

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    private void shutdownMinecraftApplet(CallbackInfo info) {
        FischMod.INSTANCE.stop();
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void runTick(CallbackInfo ignored) {

        if (thePlayer != null && !isSingleplayer()) {
            try {
                JsonObject playerInfo = playerAPI.getPlayerInfo(thePlayer.getUniqueID().toString());

                if (playerInfo == null) {
                    boolean added = playerAPI.addPlayer(thePlayer.getName(), thePlayer.getUniqueID().toString(), true);
                    if (added) {
                        ConsoleUtil.info("Spieler erfolgreich zur Datenbank hinzugefügt: " + thePlayer.getName());
                    } else {
                        ConsoleUtil.error("Fehler beim Hinzufügen des Spielers zur Datenbank.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        if (thePlayer != null) {
            if (isSingleplayer()) {
                RPC.update("Playing Singleplayer", "Exploring the world");
            }else {
                ServerData serverData = getCurrentServerData();
                if (serverData != null) {
                    RPC.update("Playing on ", serverData.serverIP);
                }
            }
        }
*/
        new GameTickEvent().call();
    }
}
