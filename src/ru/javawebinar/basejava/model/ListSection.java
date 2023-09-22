package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {

    private final List<String> list = new ArrayList<>();

    public void addToList(String str) {
        list.add(str);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append("\n");
        }
        return sb.toString();
    }
}
