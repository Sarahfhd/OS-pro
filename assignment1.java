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

        while (completedProcesses < processes.size()) {
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && !queue.contains(process) && process.remainingTime > 0) {
                    queue.add(process);
                }
            }

            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                if (currentProcess.remainingTime > TIME_QUANTUM) {
                    currentTime += TIME_QUANTUM;
                    currentProcess.remainingTime -= TIME_QUANTUM;
                    queue.add(currentProcess);
                } else {
                    currentTime += currentProcess.remainingTime;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;
                    currentProcess.remainingTime = 0;
                    completedProcesses++;
                }
            } else {
                currentTime++;
            }
        }

        displayResults(processes, totalWaitingTime, totalTurnaroundTime);
    }

    private static void displayResults(List<Process> processes, int totalWaitingTime, int totalTurnaroundTime) {
        System.out.println("\nProcess\tTurnaround Time\tWaiting Time");
        for (Process process : processes) {
            System.out.println("P" + process.id + "\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
        }

        double averageWaitingTime = (double) totalWaitingTime / processes.size();
        double averageTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        double cpuUtilization = 100.0; // Since context switching is ignored

        System.out.println("\nPerformance Metrics:");
        System.out.printf("Average Turnaround Time: %.2f\n", averageTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f\n", averageWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
}