import org.example.ListsCompare;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ListsCompareTest {

    private static final int ITERS = 1000;

    @Test
    public void testArrayList() {
        List<Integer> arrayList = new ArrayList<>();
        long addTimer = ListsCompare.testAdd(arrayList, ITERS);
        assertTrue(addTimer >= 0, "Add timer is not negative");
        long getTimer = ListsCompare.testGet(arrayList, ITERS);
        assertTrue(getTimer >= 0, "Get timer is not negative");
        long removeTimer = ListsCompare.testRemove(arrayList, ITERS);
        assertTrue(removeTimer >= 0, "Remove timer is not negative");
    }

    @Test
    public void testLinkedList() {
        List<Integer> linkedList = new LinkedList<>();
        long addTimer = ListsCompare.testAdd(linkedList, ITERS);
        assertTrue(addTimer >= 0, "Add timer is not negative");
        long getTimer = ListsCompare.testGet(linkedList, ITERS);
        assertTrue(getTimer >= 0, "Get timer is not negative");
        long removeTimer = ListsCompare.testRemove(linkedList, ITERS);
        assertTrue(removeTimer >= 0, "Remove timer is not negative");
    }
}
