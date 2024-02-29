package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", (ps) -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            return ps.execute();
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            return ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid.trim(), rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", (ps) -> {
            ps.setString(1, uuid);
            return ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS row_count FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("row_count");
            }
            return 0;
        });
    }
}
