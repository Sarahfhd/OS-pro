import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
}

public class Assignment1 {
    private static final int TIME_QUANTUM = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int numberOfProcesses = scanner.nextInt();

        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.print("Enter arrival time for Process P" + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process P" + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        simulateRoundRobin(processes);
    }

    private static void simulateRoundRobin(List<Process> processes) {
        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        int completedProcesses = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Output header information
        System.out.println("Number of processes=" + processes.size() + " (P1, P2, P3)");
        System.out.println("Arrival times and burst times as follows:");
        for (Process process : processes) {
            System.out.println("P" + process.id + ": Arrival time = " + process.arrivalTime + ", Burst time = " + process.burstTime);
        }
        System.out.println("Scheduling Algorithm: Round Robin (Time Quantum = " + TIME_QUANTUM + ")");
        
        System.out.println("Time       Process");

        while (completedProcesses < processes.size()) {
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !queue.contains(process) && process.remainingTime > 0) {
                    queue.add(process);
                }
            }

            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                int timeSlice = Math.min(currentProcess.remainingTime, TIME_QUANTUM);

                // Print the time slot for the process
                System.out.printf("%-10d P%d\n", currentTime, currentProcess.id);
                currentTime += timeSlice;
                currentProcess.remainingTime -= timeSlice;

                if (currentProcess.remainingTime == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;
                    completedProcesses++;
                } else {
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }

        displayResults(processes, totalWaitingTime, totalTurnaroundTime);
    }

    private static void displayResults(List<Process> processes, int totalWaitingTime, int totalTurnaroundTime) {
        System.out.println("\nPerformance Metric");
        double averageWaitingTime = (double) totalWaitingTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double cpuUtilization = 100.0; // Since context switching is ignored

        System.out.printf("Average Turnaround Time: %.2f -\n", averageTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f\n", averageWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
}