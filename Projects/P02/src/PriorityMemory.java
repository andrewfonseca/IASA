import java.util.Queue;

public class PriorityMemory extends SearchMemory implements Comparator {

    public PriorityMemory(Queue<Node> frontier) {
        super(frontier);
    }
}

// pee procura espaco estado
// mem memoria
// modprob modelacao problema
// mecproc mecanismo