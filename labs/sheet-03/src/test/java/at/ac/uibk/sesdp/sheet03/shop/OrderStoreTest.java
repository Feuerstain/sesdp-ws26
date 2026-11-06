package at.ac.uibk.sesdp.sheet03.shop;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OrderStoreTest {

    private final FileOrderStore store = new FileOrderStore("orders.db");
    private final Order order =
            Order.builder().id("o1").customer("Anna Huber").email("anna@example.com").build();

    @Test
    void load_afterSave_returnsTheOrder() {
        store.save(order);

        assertSame(order, store.load("o1"));
    }

    @Test
    void load_unknownId_throws() {
        assertThrows(IllegalArgumentException.class, () -> store.load("missing"));
    }

    @Test
    void archive_load_readsFromTheUnderlyingStore() {
        store.save(order);
        OrderReader archive = new ArchiveOrderStore(store);

        assertSame(order, archive.load("o1"));
    }

    @Test
    void archive_isNotAnOrderWriter_soSaveCannotBeCalledOnIt() {
        assertFalse(OrderWriter.class.isAssignableFrom(ArchiveOrderStore.class));
    }
}
