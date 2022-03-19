package pl.crystalek.sokoban.game;

public final class Player {
    private int column;
    private int row;

    public Player(final int column, final int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(final int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(final int row) {
        this.row = row;
    }
}
