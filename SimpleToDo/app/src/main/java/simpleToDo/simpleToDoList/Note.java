package simpleToDo.simpleToDoList;
import java.io.Serializable;



public class Note  {
    private String title;
    private String text;

    public Note() {
    }

    public Note(String title) {
        this.title = title;
    }

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
