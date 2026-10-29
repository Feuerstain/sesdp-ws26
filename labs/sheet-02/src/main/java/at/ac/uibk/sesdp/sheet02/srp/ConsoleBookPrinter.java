package at.ac.uibk.sesdp.sheet02.srp;

import java.io.PrintStream;

// Prints a book as plain text. Changes only if the console output format changes.
public class ConsoleBookPrinter implements BookPrinter {

    private final PrintStream out;

    public ConsoleBookPrinter(PrintStream out) {
        this.out = out;
    }

    @Override
    public void print(Book book) {
        out.println(book.getTitle());
        out.println(book.getContent());
    }
}
