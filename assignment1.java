import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class Assignment1 {
    private static final int TIME_QUANTUM = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int numProcesses = scanner.nextInt();
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter Arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter Burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        simulateRoundRobin(processes);
        scanner.close();
    }

    private static void simulateRoundRobin(List<Process> processes) {
        Queue<Process> queue = new LinkedList<>();
        List<String> ganttChart = new ArrayList<>();
        int currentTime = 0;
        int completedProcesses = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        while (completedProcesses < processes.size()) {
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && !queue.contains(p)) {
                    queue.add(p);
                }
            }

            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                int timeSpent = Math.min(TIME_QUANTUM, currentProcess.remainingTime);
                ganttChart.add(String.format("%d-%d\t\tP%d", currentTime, currentTime + timeSpent, currentProcess.id));
                currentTime += timeSpent;
                currentProcess.remainingTime -= timeSpent;

                if (currentProcess.remainingTime == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;
                    totalWaitingTime += currentProcess.waitingTime;
                    completedProcesses++;
                } else {
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }

        printGanttChart(ganttChart);
        printPerformanceMetrics(processes.size(), totalTurnaroundTime, totalWaitingTime);
    }

    private static void printGanttChart(List<String> ganttChart) {
        System.out.println("\nTime\t\tProcess");
        for (String entry : ganttChart) {
            System.out.println(entry);
        }
    }

    private static void printPerformanceMetrics(int numProcesses, int totalTurnaroundTime, int totalWaitingTime) {
        double averageTurnaroundTime = (double) totalTurnaroundTime / numProcesses;
        double averageWaitingTime = (double) totalWaitingTime / numProcesses;
        double cpuUtilization = 100.0; // Ignoring context switching as per requirements

        System.out.println("\nPerformance Metrics:");
        System.out.printf("Average Turnaround Time: %.2f\n", averageTurnaroundTime);
        System.out.printf("Average Waiting Time: %.2f\n", averageWaitingTime);
        System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
    }
}