package pl.crystalek.sokoban.settings;

import java.io.Serializable;

public final class Settings implements Serializable {
    private static final long serialVersionUID = 5179680418474481551L;
    private Sound sound = Sound.ENABLE;
    private Control controlType = Control.WSAD;
    private double brightness = 0;

    public Settings() {
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(final Sound sound) {
        this.sound = sound;
    }

    public Control getControlType() {
        return controlType;
    }

    public void setControlType(final Control controlType) {
        this.controlType = controlType;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(final double brightness) {
        this.brightness = brightness;
    }
}
