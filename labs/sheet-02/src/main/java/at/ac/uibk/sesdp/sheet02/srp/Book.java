package at.ac.uibk.sesdp.sheet02.srp;

// Responsibility: holding the data of a book. Changes only if the book data changes.
public class Book {

    private final String title;
    private final String content;

    public Book(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
