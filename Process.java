import java.util.Objects;

public class Process {

    int arrivalTime, remainingTime, priority, burstTime, quantum;

    int order, waitingTime, turnaroundTime;
    String name;

    Process Clone() {
        return new Process(arrivalTime, burstTime, priority, name, quantum);
    }

    public Process(int arrivalTime, int burstTime, int priority, String name, int quantum) {
        this.arrivalTime = arrivalTime;
        this.remainingTime = burstTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.name = name;
        this.order = -1;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.quantum = quantum;
    }

    public Process(Process p) {
        this.arrivalTime = p.arrivalTime;
        this.remainingTime = p.remainingTime;
        this.priority = p.priority;
        this.name = p.name;
    }
}
