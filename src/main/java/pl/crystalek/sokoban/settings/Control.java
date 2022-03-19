package pl.crystalek.sokoban.settings;

public enum Control {
    WSAD("WSAD"),
    ARROWS("Strzałki");

    private final String name;

    Control(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
