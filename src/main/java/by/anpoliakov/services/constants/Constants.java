package by.anpoliakov.services.constants;

/**
 * Класс с константными значениями, которые применяются в приложении
 * для более простого
 * */
public class Constants {
    public static final String PATH_TO_PROPERTIES_DB = "src/main/resources/db.constants/database.properties";
    public static final String PATH_TO_CHANGELOG_FILE = "db/changelog/changelog.xml";
    public static final String DRIVER_NAME = "org.postgresql.Driver";

    //временное решение связывния Enum локального и таблицы Enum в БД (в дальнейшем буду получать через вложенный запрос SQL)
    public static final int CORRECT_VALUE_ENUM_FOR_DATABASE = 1;
}
