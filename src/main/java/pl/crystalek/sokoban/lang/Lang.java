package pl.crystalek.sokoban.lang;

public final class Lang {
    public static final String TITLE_ERROR = "Błąd";
    public static final String TITLE_INFO = "Informacja";
    public static final String TITLE_WARNING = "Ostrzeżenie";

    //ExportMap
    public static final String DO_YOU_WANT_EXPORT_MAP = "Czy na pewno chcesz wyeksportować mapę bez aktualnych poprawek?";
    public static final String CHOOSE_SAVE_LOCATION = "Wybierz miejsce zapisu mapy";
    public static final String NOT_CHOOSE_LOCATION = "Nie wybrano lokalizacji!"; //ImportMap
    public static final String EXPORT_FILE_ERROR = "Wystąpił błąd podczas próby stworzenia pliku mapy!";

    //ImportMap
    public static final String DO_YOU_WANT_LOAD_MAP = "Masz niezapisane zmiany na aktualnie edytowanej mapie, czy na pewno chcesz załadować nową mapę?";
    public static final String CHOOSE_MAP_LOCATION = "Wybierz lokaliacje pliku mapy";
    public static final String LOAD_FILE_ERROR = "Wczytanie nie powiodło się";
    public static final String EMPTY_FILE_CONTENT = "Plik mapy nie może być pusty!";
    public static final String TOO_MANY_ROWS = "Mapa nie może mieć więcej niż 20 rzędów!";
    public static final String TOO_MANY_COLUMNS = "Mapa nie może mieć więcej niż 30 kolumn!";
    public static final String NOT_ALLOWED_SIGN = "W pliku mapy wykryto niedozwolony znak.";

    //MapEditorController
    public static final String DO_YOU_WANT_LEAVE_EDITOR = "Czy na pewno chcesz opuscic edytor? Zapisz mape, w przeciwnym wypadku utracisz niezapisane zmiany.";
    public static final String DO_YOU_WANT_RESET_MAP = "Czy na pewno chcesz zresetować mapę?";

    //MapSettingsController
    public static final String NOT_EMPTY_FIELD = "Pola nie mogą być puste!";
    public static final String MAP_NAME_EXIST = "Taka nazwa mapy już istnieje!";
    public static final String RANGE_FIELD_ERROR = "Pola muszą być liczbą całkowitą z zakresu 0 - " + Integer.MAX_VALUE;
    public static final String SAVE_MAP_SETTINGS = "Ustawienia mapy zostaly pomyślnie zapisane.";
    public static final String SAVE_MAP_AND_SETTINGS = "Mapa i jej ustawienia zostaly pomyslnie zapisane.";

    //SaveMap
    public static final String NO_CHANGE_TO_SAVE = "Brak zmian do zapisu"; //GameController
    public static final String SAVE_MAP = "Mapa zostala pomyslnie zapisana!";

    //GameController
    public static final String DO_YOU_WANT_EXIT_GAME = "Masz niezapisane zmiany, czy na pewno chcesz wyjsc z gry?"; //CloseGameListener
    public static final String SAVE_MAP_PROGRESS = "Zapis zostal pomyslnie zapisany!";
    public static final String ENTER_MAP_NAME = "Podaj nazwe zapisu"; //ChangeNameController

    //LoadGameController
    public static final String DO_YOU_WANT_DELETE = "Czy na pewno chcesz usunąć ";
    public static final String DELETE_PROGRESS = "ten zapis?";
    public static final String DELETE_MAP = "tą mapę?";

    //LoadMapToEditController
    public static final String DO_YOU_WANT_DELETE_MAP = "Czy na pewno chcesz usunąć tą mapę?";
    public static final String DO_YOU_WANT_LOAD_NEW_MAP = "Czy na pewno chcesz wczytać nową mape? Posiadasz niezapisane zmiany na aktualnie edytowanej mapie! Jeśli klikniesz tak, zmiany zostaną utracone.";

    //LoadUtil
    public static final String PROGRESS_INFO_LABEL_TEXT = "Data stworzenia: {CREATION_DATE} \nData modyfikacji: {MODIFICATION_DATE} \nZapisana mapa: {NAME}";
    public static final String MAP_INFO_LABEL_TEXT = "Data stworzenia: {CREATION_DATE} \nData modyfikacji: {MODIFICATION_DATE}";
    public static final String DIFFICULTY = "Trudność: {DIFFICULTY}";

    //ChangeNameController
    public static final String PROGRESS_NAME_EXIST = "Taka nazwa zapisu już istnieje!";

    //ConfirmationController
    public static final String MAP_CLEAR = "Mapa zostala wyczyszczona";

    //Game
    public static final String MAX_PLAYER_ON_MAP = "Liczba graczy na mapie musi być równa 1!";
    public static final String ITEM_ERROR_COUNT = "Ilość skrzyń i bomb musi być równa i różna od zera!";

    //FileLoader
    public static final String ERROR_WHILE_LOADING_GAMES_FILE = "Wystapił błąd podczas ładowania plików gry!";
    public static final String ERROR_WHILE_LOADING_FILE = "Wystapił błąd podczas ładowania pliku: {FILE_NAME}";

    //FileManager
    public static final String CREATE_SETTINGS_FILE = "Plik konfiguracyjny został utworzony!";
    public static final String CREATE_RANKING_FILE = "Plik rankingowy został utworzony!";
    public static final String ERROR_WHILE_CREATING = "Wystapił błąd podczas próby tworzenia plików konfiguracyjnych";

    //FileSaver
    public static final String ERROR_WHILE_SAVING_FILE = "Wystapił błąd podczas zapisywania pliku: {FILE_NAME}";
    public static final String ERROR_WHILE_SAVING_MAP_FILE = "Wystapił błąd podczas zapisywania mapy: {MAP_NAME}";

    //MapLoader
    public static final String ERROR_WHILE_LOADING_MAP_FILE = "Wystapił błąd podczas ładowania mapy: {MAP_NAME}";

    //ProgressLoader
    public static final String ERROR_WHILE_LOADING_PROGRESS_FILE = "Wystapił błąd podczas ładowania zapisów gry: {PROGRESS_NAME}";

    //Sokoban
    public static final String SAVING_COMPLETE = "Zapisywanie zostalo zakonczone";
}
