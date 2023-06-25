import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

// Preemptive Priority Scheduling
public class PPRI {
    public static void run(List<Process> list) {
        System.out.println("**Preemptive Priority Scheduling**\n");
        PriorityQueue<Process> q = new PriorityQueue<>(list.size(), Comparator.comparingInt(o -> o.priority));
        list.sort(Comparator.comparingInt(o -> o.arrivalTime));
        double avgWaitingTime = 0, avgTurnaroundTime = 0;
        int cur = 0;
        int time = list.get(cur).arrivalTime;
        q.add(list.get(cur++));
        int leave = 0;
        Vector<String> order = new Vector<>();
        while (!q.isEmpty()) {
            Process p = q.poll();
            time++;
            p.remainingTime--;
            order.add(p.name);
            while (cur < list.size() && list.get(cur).arrivalTime <= time) {
                Process temp = list.get(cur++);
                temp.priority -= leave;
                q.add(temp);
            }
            if (p.remainingTime > 0) {
                q.add(p);
            } else {
                p.turnaroundTime = time - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                avgTurnaroundTime += p.turnaroundTime;
                avgWaitingTime += p.waitingTime;
                leave++;
            }
            if (q.isEmpty() && cur < list.size()) {
                time += (list.get(cur).arrivalTime - time);
                Process temp = list.get(cur++);
                temp.priority -= leave;
                q.add(temp);
            }
        }
        Main.Print(list, avgWaitingTime, avgTurnaroundTime, order);
    }
}
