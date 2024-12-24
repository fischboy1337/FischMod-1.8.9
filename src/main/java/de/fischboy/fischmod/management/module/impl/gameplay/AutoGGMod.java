package de.fischboy.fischmod.management.module.impl.gameplay;

import de.fischboy.fischmod.api.utils.Utils;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;
import de.fischboy.fischmod.management.module.Module;
import de.fischboy.fischmod.management.module.ModuleCategory;
import de.fischboy.fischmod.management.module.annotations.ModInfo;
import de.fischboy.fischmod.management.module.value.impl.SliderValue;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModInfo(name = "AutoGG", description = "Automatically says GG in the Chat", category = ModuleCategory.GAMEPLAY)
public class AutoGGMod extends Module {

    public SliderValue cooldownTime = new SliderValue("Delay", 0, 10, 2, 1);

    private long cooldown = 0;
    public static String endGameMSG = "gg";

    String[] triggers = {
            "1st Killer - ",
            "1st Place - ",
            " - Damage Dealt - ",
            "Winning Team: ",
            "1st - ",
            "Winners: ",
            "Winner: ",
            " won the game!",
            "Top Seeker: ",
            "1st Place: ",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "Winners - ",
            "Sumo Duel - ",
    };

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (Utils.isHypixel() && isEnabled() && (cooldown == 0 || cooldown + cooldownTime.getValue() < Minecraft.getSystemTime())) {
            String msg = event.message.getUnformattedText().trim();
            ConsoleUtil.info("AutoGG: " + msg);
            for (String trigger : triggers) {
                if (msg.startsWith(trigger.trim())) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/achat " + endGameMSG);
                    cooldown = Minecraft.getSystemTime();
                    break;
                }
            }
        }
    }
}
