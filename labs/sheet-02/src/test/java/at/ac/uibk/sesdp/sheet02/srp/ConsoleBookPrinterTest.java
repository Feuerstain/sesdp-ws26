package at.ac.uibk.sesdp.sheet02.srp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConsoleBookPrinterTest {

    @Test
    void print_writesTitleAndContentOnSeparateLines() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BookPrinter printer = new ConsoleBookPrinter(new PrintStream(output));

        printer.print(new Book("Interesting title", "Nice content"));

        String expected = "Interesting title" + System.lineSeparator()
                + "Nice content" + System.lineSeparator();
        assertEquals(expected, output.toString());
    }

    @Test
    void book_canBeOutputByAnyPrinterWithoutChangingBook() {
        List<String> printedTitles = new ArrayList<>();
        BookPrinter titleOnlyPrinter = book -> printedTitles.add(book.getTitle());

        titleOnlyPrinter.print(new Book("Interesting title", "Nice content"));

        assertEquals(List.of("Interesting title"), printedTitles);
    }
}
