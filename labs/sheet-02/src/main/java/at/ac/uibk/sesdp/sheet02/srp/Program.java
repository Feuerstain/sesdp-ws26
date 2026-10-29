package at.ac.uibk.sesdp.sheet02.srp;

public class Program {

    public static void main(String[] args) {
        Book book = new Book("Interesting title", "Nice content");
        BookPrinter printer = new ConsoleBookPrinter(System.out);
        printer.print(book);
    }
}
