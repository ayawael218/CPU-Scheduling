import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter The number of processes: ");
        int n = sc.nextInt();
        System.out.println("Enter Round robin Time Quantum: ");
        int rrtq = sc.nextInt();
        System.out.println("Enter Context switching: ");
        int cSwitch = sc.nextInt();
        List<Process> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Enter Process Name:");
            String processName = sc.next();
            System.out.println("Enter " + processName + "'s Arrival time:");
            int arrivalTime = sc.nextInt();
            System.out.println("Enter " + processName + "'s Burst time:");
            int burstTime = sc.nextInt();
            System.out.println("Enter " + processName + "'s Priority:");
            int priority = sc.nextInt();
            System.out.println("Enter " + processName + "'s Quantum (AG):");
            int quantum = sc.nextInt();
            Process p = new Process(arrivalTime, burstTime, priority, processName, quantum);
            list.add(p);
        }
        List<Process> temp = new ArrayList<>();
        for (Process p : list)
            temp.add(p.Clone());
        PSJF.run(temp, cSwitch);
        temp.clear();
        for (Process p : list)
            temp.add(p.Clone());
        RR.run(temp, rrtq, cSwitch);
        temp.clear();
        for (Process p : list)
            temp.add(p.Clone());
        PPRI.run(temp);
        temp.clear();
        for (Process p : list)
            temp.add(p.Clone());
        AG.run(temp);
    }

    static void Print(List<Process> list, double avgWaitingTime, double avgTurnaroundTime, Vector<String> order) {
        System.out.println("Process execution order:");
        for (String s : order)
            System.out.print(s + " ");
        System.out.println("\n\nProcess name \t\t\t\t\t Waiting Time \t\t\t\t\t Turnaround Time");
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i).name + "\t\t\t\t\t\t\t\t\t" + list.get(i).waitingTime + "\t\t\t\t\t\t\t\t" + list.get(i).turnaroundTime);
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime / list.size());
        System.out.println("Average Waiting Time: " + avgWaitingTime / list.size() + "\n\n");
    }
}