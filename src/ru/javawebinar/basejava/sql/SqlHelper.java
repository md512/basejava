package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, QueryCode <T> queryCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return queryCode.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                int startMessageIndex = e.getMessage().indexOf("(uuid)=(") + 8;
                String wrongUuid = e.getMessage().substring(startMessageIndex, startMessageIndex + 36).trim();
                throw new ExistStorageException(wrongUuid);
            }
            throw new StorageException(e);
        }
    }
}
