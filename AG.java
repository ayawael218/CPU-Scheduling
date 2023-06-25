import java.util.*;

import static java.lang.Math.min;

public class AG {
    public static void run(List<Process> list) {
        System.out.println("**AG Scheduling**\n");
        Queue<Process> q = new ArrayDeque<>();
        PriorityQueue<Process> pq = new PriorityQueue<>(list.size(), Comparator.comparingInt(o -> o.remainingTime));
        list.sort(Comparator.comparingInt(o -> o.arrivalTime));
        double avgWaitingTime = 0, avgTurnaroundTime = 0;
        int cur = 0;
        int time = list.get(cur).arrivalTime;
        q.add(list.get(cur++));
        Vector<String> order = new Vector<>();
        while (!q.isEmpty()) {
            Process p = q.poll();
            order.add(p.name);
            int used = p.burstTime - p.remainingTime;
            if (used <= (p.quantum + p.quantum - 3) / 4) {
                //FCFS
                int curQuantum = p.quantum;
                System.out.print(p.name + "'s quantum: " + curQuantum + " -> ");
                time += (p.quantum + p.quantum - 3) / 4;
                p.remainingTime -= (p.quantum + p.quantum - 3) / 4;
                while (cur < list.size() && list.get(cur).arrivalTime <= time) {
                    q.add(list.get(cur++));
                }
                if (p.remainingTime > 0) {
                    p.quantum += 2;
                    q.add(p);
                } else {
                    p.turnaroundTime = time - p.arrivalTime;
                    p.waitingTime = p.turnaroundTime - p.burstTime;
                    avgTurnaroundTime += p.turnaroundTime;
                    avgWaitingTime += p.waitingTime;
                }
                System.out.println(p.quantum);
            } else if (used > (p.quantum + p.quantum - 3) / 4 && used <= (p.quantum + p.quantum - 1) / 2) {
                int curQuantum = p.quantum;
                System.out.print(p.name + "'s quantum: " + curQuantum + " -> ");
                time += (p.quantum + p.quantum - -1) / 2;
                p.remainingTime -= (p.quantum + p.quantum - 1) / 2;
                if (p.remainingTime > 0) {
                    p.quantum += (p.quantum - ((p.quantum + p.quantum - 1) / 2)) / 2;
                    q.add(p);
                } else {
                    p.turnaroundTime = time - p.arrivalTime;
                    p.waitingTime = p.turnaroundTime - p.burstTime;
                    avgTurnaroundTime += p.turnaroundTime;
                    avgWaitingTime += p.waitingTime;
                }
                System.out.println(p.quantum);
            } else {
                int curQuantum = p.quantum;
                System.out.print(p.name + "'s quantum: " + curQuantum + " -> ");
                time += (p.quantum - ((p.quantum + p.quantum - 1) / 2));
                p.remainingTime -= (p.quantum - ((p.quantum + p.quantum - 1) / 2));
                if (p.remainingTime > 0) {
                    p.quantum += (p.quantum - ((p.quantum + p.quantum - 1) / 2));
                    q.add(p);
                } else {
                    p.turnaroundTime = time - p.arrivalTime;
                    p.waitingTime = p.turnaroundTime - p.burstTime;
                    avgTurnaroundTime += p.turnaroundTime;
                    avgWaitingTime += p.waitingTime;
                }
                if (q.isEmpty() && cur < list.size()) {
                    time += (list.get(cur).arrivalTime - time);
                    q.add(list.get(cur++));
                }
                System.out.println(p.quantum);
            }
//            time += min(p.remainingTime, quantum);
//            p.remainingTime -= quantum;
//            order.add(p.name);
//            while (cur < list.size() && list.get(cur).arrivalTime <= time) {
//                q.add(list.get(cur++));
//            }
//            if (p.remainingTime > 0) {
//                q.add(p);
//            } else {
//                p.turnaroundTime = time - p.arrivalTime;
//                p.waitingTime = p.turnaroundTime - p.burstTime;
//                avgTurnaroundTime += p.turnaroundTime;
//                avgWaitingTime += p.waitingTime;
//            }
//            if (q.isEmpty() && cur < list.size()) {
//                time += (list.get(cur).arrivalTime - time);
//                q.add(list.get(cur++));
//            }
        }
        Main.Print(list, avgWaitingTime, avgTurnaroundTime, order);
    }
}
