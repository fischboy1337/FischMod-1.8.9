package de.fischboy.fischmod.gui.clickGui.component.sub;

import de.fischboy.fischmod.gui.clickGui.component.Component;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class SubComponent<T> extends Component {

    protected final T value;

    public SubComponent(T value, float x, float y, float width, float height) {
        super(null, x, y, width, height);
        this.value = value;
    }

}