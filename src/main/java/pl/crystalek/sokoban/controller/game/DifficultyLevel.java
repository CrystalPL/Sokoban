package pl.crystalek.sokoban.controller.game;

enum DifficultyLevel {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int priority;

    DifficultyLevel(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}