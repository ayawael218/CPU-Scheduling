import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

// ROUND ROBIN
public class RR {
    public static void run(List<Process> list, int quantum, int cSwitch) {
        Queue<Process> q = new ArrayDeque<>();
        list.sort(Comparator.comparingInt(o -> o.arrivalTime));
        double avgWaitingTime = 0, avgTurnaroundTime = 0;
        int cur = 0;
        int time = list.get(cur).arrivalTime;
        q.add(list.get(cur++));
        Vector<String> order = new Vector<>();
        while (!q.isEmpty()) {
            Process p = q.poll();
            time += min(p.remainingTime, quantum);
            p.remainingTime -= quantum;
            order.add(p.name);
            while (cur < list.size() && list.get(cur).arrivalTime <= time) {
                q.add(list.get(cur++));
            }
            if (p.remainingTime > 0) {
                q.add(p);
                time += cSwitch;
            } else {
                time += cSwitch;
                p.turnaroundTime = time - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                avgTurnaroundTime += p.turnaroundTime;
                avgWaitingTime += p.waitingTime;
            }
            if (q.isEmpty() && cur < list.size()) {
                time += (list.get(cur).arrivalTime - time);
                q.add(list.get(cur++));
            }
        }
        System.out.println("**Round Robin Scheduler**\n");
        Main.Print(list, avgWaitingTime, avgTurnaroundTime, order);
    }
}
