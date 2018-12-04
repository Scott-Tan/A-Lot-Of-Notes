package com.example.a_lot_of_notes.a_lot_of_notes;

public class ListRow {
    private String title;
    private String date;
    private String file;
    private String type;

    public ListRow(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public ListRow(String title, String date, String file, String type) {
        this.title = title;
        this.date = date;
        this.file = file;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
