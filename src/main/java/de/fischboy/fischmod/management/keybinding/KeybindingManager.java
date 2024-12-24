package de.fischboy.fischmod.management.keybinding;

import de.fischboy.fischmod.FischMod;
import de.fischboy.fischmod.management.event.EventTarget;
import de.fischboy.fischmod.management.event.impl.GameTickEvent;
import de.fischboy.fischmod.management.keybinding.impl.ClientMenuKeybind;
import de.fischboy.fischmod.api.utils.containers.Storage;
import lombok.Getter;
import org.lwjgl.input.Keyboard;

@Getter
public class KeybindingManager extends Storage<ClientKeybinding> {


    @Override
    public void onStart() {
        this.addAll(
                ClientMenuKeybind::new
        );
        FischMod.INSTANCE.getEventManager().register(this);
    }

    @EventTarget
    public void onTick(GameTickEvent event) {
        this.stream().filter(k -> Keyboard.isKeyDown(k.getKeyCode())).forEach(ClientKeybinding::onKeyPressed);
    }
}
