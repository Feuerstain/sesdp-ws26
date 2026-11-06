package at.ac.uibk.sesdp.sheet03.shop;

// Read-only view on another store. It implements only the reading interface,
// so save() does not exist here and nothing has to throw UnsupportedOperationException.
public class ArchiveOrderStore implements OrderReader {

    private final OrderReader source;

    public ArchiveOrderStore(OrderReader source) {
        this.source = source;
    }

    @Override
    public Order load(String id) {
        return source.load(id);
    }
}
