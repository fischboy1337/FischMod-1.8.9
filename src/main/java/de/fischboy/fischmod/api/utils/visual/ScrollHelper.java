package de.fischboy.fischmod.api.utils.visual;

import de.fischboy.fischmod.api.utils.animation.Animate;
import de.fischboy.fischmod.api.utils.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;

@Getter @Setter
public class ScrollHelper {

    private float scrollStep = 0;
    private boolean direction = true;
    private final Animate animate = new Animate();
    private float minScroll, maxScroll, height;
    private float calculatedScroll;
    private final int scrollStepSize;

    public ScrollHelper(int minScroll, int maxScroll, int scrollStepSize, int speed) {
        this.minScroll = minScroll;
        this.maxScroll = maxScroll;
        this.height = 0;
        this.scrollStepSize = scrollStepSize;
        animate.setEase(Easing.CUBIC_IN_OUT).setMin(0).setMax(scrollStepSize).setSpeed(speed);
    }

    public void update() {
        animate.update();
        calculatedScroll = direction ?
                scrollStep * scrollStepSize + animate.getValueF() - scrollStepSize :
                scrollStep * scrollStepSize - animate.getValueF() + scrollStepSize;
    }

    public void updateScroll() {
        int scroll = Mouse.getDWheel();
        if (scroll > 0) {
            if (scrollStep * scrollStepSize < minScroll) {
                scrollStep++;
                direction = true;
                animate.reset();
            }
        } else if (scroll < 0) {
            if ((scrollStep * scrollStepSize + height) > maxScroll) {
                scrollStep--;
                direction = false;
                animate.reset();
            }
        }
    }
}
