package com.example.a_lot_of_notes.a_lot_of_notes;

public class Note {

    private int id;
    private String title;
    private String note;

    public Note(){

    }


    /* Note constructors - START*/
    public Note(int id,String title,String note){
        this.id = id;
        this.title = title;
        this.note = note;
    }

    public Note(String title,String note){
        this.title = title;
        this.note = note;
    }
    /* Note constructors - END */


    /* Accessors */

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }


    /* Setters */
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }
}