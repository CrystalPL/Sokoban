package pl.crystalek.sokoban.settings;

public enum Sound {
    ENABLE("Włączony"),
    DISABLE("Wyłączony");

    private final String name;

    Sound(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
