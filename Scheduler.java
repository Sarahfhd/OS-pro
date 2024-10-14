
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
    private ArrayList<Process> processes = new ArrayList<>();
    private Queue<Process> queue = new LinkedList<>();
    private int timeQuantum = 2;

    public void addProcess(Process process) {
        processes.add(process);
    }

   public void roundRobinScheduling() {
    int currentTime = 0;
    ArrayList<Process> completedProcesses = new ArrayList<>();
    
    // Sort processes by arrival time
    processes.sort((p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));

    // Enqueue the first process that arrives
    queue.add(processes.get(0));
    int index = 1;
    
    System.out.println("Scheduling Algorithm: Round Robin (Time Quantum = " + timeQuantum + ")");
    System.out.println("Time\tProcess");

    // Process each job in the queue
    while (!queue.isEmpty()) {
        Process currentProcess = queue.poll();
        
        // Check if the process has remaining time
        if (currentProcess.getRemainingTime() > timeQuantum) {
            System.out.println(currentTime + "-" + (currentTime + timeQuantum) + "\tP" + currentProcess.getPid());
            currentTime += timeQuantum;
            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeQuantum);
        } else {
            System.out.println(currentTime + "-" + (currentTime + currentProcess.getRemainingTime()) + "\tP" + currentProcess.getPid());
            currentTime += currentProcess.getRemainingTime();
            currentProcess.setRemainingTime(0);
            currentProcess.setCompletionTime(currentTime);
            currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
            currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
            completedProcesses.add(currentProcess);
        }

        // Add new arrivals that have arrived during or just after the time quantum
        while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
            queue.add(processes.get(index));
            index++;
        }

        // Requeue the current process if it's not finished
        if (currentProcess.getRemainingTime() > 0) {
            queue.add(currentProcess);
        }
    }

    displayMetrics(completedProcesses);
}
public void displayMetrics(ArrayList<Process> completedProcesses) {
    double totalTurnaroundTime = 0;
    double totalWaitingTime = 0;
    int totalBurstTime = 0;

    for (Process p : completedProcesses) {
        totalTurnaroundTime += p.getTurnaroundTime();
        totalWaitingTime += p.getWaitingTime();
        totalBurstTime += p.getBurstTime();
    }

    double avgTurnaroundTime = totalTurnaroundTime / completedProcesses.size();
    double avgWaitingTime = totalWaitingTime / completedProcesses.size();
    
    // Calculate the total time from the start of the first process to the completion of the last process
    int totalCompletionTime = completedProcesses.get(completedProcesses.size() - 1).getCompletionTime();
    double cpuUtilization = ((double) totalBurstTime / totalCompletionTime) * 100;

    System.out.println("\nPerformance Metric");
    System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
    System.out.printf("Average Waiting Time: %.2f\n", avgWaitingTime);
    System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
}


}

