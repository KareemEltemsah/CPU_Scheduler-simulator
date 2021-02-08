
public class Process {
	String name;
	int arrivalTime;
	int burstTime;
	int priority;
	int queueNumber;

	int remainingTime = 0;// burst time - work done
	int waitingTime = 0;
	int turnaroundTime = 0;
	boolean isFinished = false;// flag to determine the finished processes
	int finishTime = 0;

	public Process(String n, int arrival, int burst) {
		name = n;
		arrivalTime = arrival;
		burstTime = burst;
		remainingTime = burstTime;
	}

	@Override // override toString in case i needed to print processes
	public String toString() {
		return name + "," + arrivalTime + "," + burstTime + ",";
	}
}
