package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            setContacts(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            setContacts(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    getContacts(rs, r);
//                    do {
//                        String value = rs.getString("value");
//                        ContactType type = ContactType.valueOf(rs.getString("type"));
//                        r.addContact(type, value);
//                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "     SELECT * FROM resume r" +
                        "  LEFT JOIN contact c " +
                        "         ON r.uuid = c.resume_uuid " +
                        "   ORDER BY full_name, uuid",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    List<String> uuids = new ArrayList<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        if (!uuids.contains(uuid)) {
                            Resume r = new Resume(uuid, rs.getString("full_name"));
                            getContacts(rs, r);
                            resumes.add(r);
                            uuids.add(uuid);
                        }
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS row_count FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("row_count") : 0;
        });
    }

    public void setContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void getContacts (ResultSet rs, Resume r) throws SQLException {
        do {
            if (rs.getString("resume_uuid").equals(r.getUuid())) {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            }
        } while (rs.next());
    }
}