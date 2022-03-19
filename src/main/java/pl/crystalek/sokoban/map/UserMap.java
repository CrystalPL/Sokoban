package pl.crystalek.sokoban.map;

import java.time.LocalDateTime;

public class UserMap extends DefaultMap {
    private static final long serialVersionUID = 6981674798801078963L;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime modificationDate = LocalDateTime.now();
    private transient boolean changesToSave = false;
    private transient String oldName;

    public UserMap() {
    }

    public UserMap(final String name) {
        super(name);
    }

    public UserMap(final String name, final boolean closeGameWhenTimeEnd, final int bonus, final int timeInSeconds) {
        super(name, closeGameWhenTimeEnd, bonus, timeInSeconds);
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(final String oldName) {
        this.oldName = oldName;
    }

    public boolean isChangesToSave() {
        return changesToSave;
    }

    public void setChangesToSave(final boolean changesToSave) {
        this.changesToSave = changesToSave;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(final LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
