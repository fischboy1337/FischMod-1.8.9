package de.fischboy.fischmod.api.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class RPC {

    public long start;
    public DiscordRPC rpc = DiscordRPC.INSTANCE;
    private DiscordEventHandlers handlers;
    public boolean initialized = false;

    public void init() {
        handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        start = System.currentTimeMillis();

        rpc.Discord_Initialize("1295092352028905563", handlers, true, "");
        initialized = true;
    }

    public static RPC INSTANCE() {
        return new RPC();
    }

    public static void update(String details, String state) {
        RPC rpc1 = new RPC();

        if (!rpc1.initialized) {
            rpc1.init();
        }
        DiscordRichPresence richPresence = new DiscordRichPresence();
        richPresence.state = state;
        richPresence.details = details;
        richPresence.largeImageKey = "large";
        richPresence.startTimestamp = rpc1.start;

        rpc1.rpc.Discord_UpdatePresence(richPresence);
    }
}
