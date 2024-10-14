
public class Process {
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int completionTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    // Getters and Setters
    public int getPid() { return pid; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int time) { this.remainingTime = time; }
    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int time) { this.completionTime = time; }
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int time) { this.waitingTime = time; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int time) { this.turnaroundTime = time; }
}

