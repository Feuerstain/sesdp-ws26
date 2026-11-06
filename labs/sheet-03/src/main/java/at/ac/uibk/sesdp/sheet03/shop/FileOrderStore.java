package at.ac.uibk.sesdp.sheet03.shop;

import java.util.HashMap;
import java.util.Map;

// Low-level detail. The file access is left out, as on the sheet ("{ ... }"):
// a map stands in for the file, export and backup only print what they would do.
public class FileOrderStore implements OrderReader, OrderWriter, OrderExporter, OrderBackup {

    private final String path;
    private final Map<String, Order> orders = new HashMap<>();

    public FileOrderStore(String path) {
        this.path = path;
    }

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Order load(String id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new IllegalArgumentException("Unknown order: " + id);
        }
        return order;
    }

    @Override
    public void exportCsv(String csvPath) {
        System.out.println("Exporting " + orders.size() + " orders from " + path + " to " + csvPath);
    }

    @Override
    public void backupToCloud(String bucket) {
        System.out.println("Backing up " + path + " to bucket " + bucket);
    }
}
