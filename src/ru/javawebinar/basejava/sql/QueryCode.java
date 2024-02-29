package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;

public interface QueryCode<T> {
    T execute(PreparedStatement ps);
}
