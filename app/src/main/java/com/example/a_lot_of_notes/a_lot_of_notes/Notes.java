package com.example.a_lot_of_notes.a_lot_of_notes;

public class Notes {

    private int id;
    private String title;
    private String note;

    public Notes(){

    }


    /* Note constructors - START*/
    public Notes(int id,String title,String note){
        this.id = id;
        this.title = title;
        this.note = note;
    }

    public Notes(String title,String note){
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