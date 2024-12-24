package de.fischboy.fischmod.api.utils.animation;

import lombok.Getter;

public class DeltaTime {

    @Getter
    private static int deltaTime = 0;

    public static void setDeltaTime(int deltaTime) {
        DeltaTime.deltaTime = deltaTime;
    }
}
