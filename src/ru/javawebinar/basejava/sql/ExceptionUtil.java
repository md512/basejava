package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                int startMessageIndex = e.getMessage().indexOf("(uuid)=(") + 8;
                String wrongUuid = e.getMessage().substring(startMessageIndex, startMessageIndex + 36);
                throw new ExistStorageException(wrongUuid);
            }
        }
        return new StorageException(e);
    }
}
