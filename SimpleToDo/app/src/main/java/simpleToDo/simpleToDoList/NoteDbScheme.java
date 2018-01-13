package simpleToDo.simpleToDoList;


public class NoteDbScheme {
    private long id;
    private String note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return note;
    }
}
