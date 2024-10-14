/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Almah
 */
import java.util.Scanner;

public class ProcessSchedulerSimulator {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter arrival time and burst time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            scheduler.addProcess(new Process(i + 1, arrivalTime, burstTime));
        }

        scheduler.roundRobinScheduling();
        scanner.close();
    }
}

