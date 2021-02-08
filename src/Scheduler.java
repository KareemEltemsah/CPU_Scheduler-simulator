import java.util.ArrayList;

public class Scheduler {
	// array to store processes
	ArrayList<Process> processes = new ArrayList<Process>();

	public void scheduling() {
		// to be override
	}

	protected boolean isAllProcessesFinished() {
		for (int i = 0; i < processes.size(); i++) {
			if (!processes.get(i).isFinished)// if any incomplete process return false
				return false;
		}
		return true;
	}

	protected void printSchedulingStatistics() {
		float avgWaitingTime = 0;
		float avgTurnaroundTime = 0;
		System.out.println("\nprocess | wating time | turnaround time");
		System.out.println(String.format("%39s", " ").replace(" ", "-"));
		for (int i = 0; i < processes.size(); i++) {
			Process p = processes.get(i);
			// waiting time = finish time - (arrival time + burst time)
			p.waitingTime = p.finishTime - (p.arrivalTime + p.burstTime);
			// turnaround time = finish time - arrival time
			p.turnaroundTime = p.finishTime - p.arrivalTime;
			avgWaitingTime += p.waitingTime;
			avgTurnaroundTime += p.turnaroundTime;
			System.out.println(centerString(8, p.name) + "|" + centerString(13, p.waitingTime + "") + "|"
					+ centerString(16, p.turnaroundTime + ""));
		}
		avgWaitingTime = avgWaitingTime / processes.size();
		avgTurnaroundTime = avgTurnaroundTime / processes.size();
		System.out.println(centerString(8, "average") + "|" + centerString(13, avgWaitingTime + "") + "|"
				+ centerString(16, avgTurnaroundTime + ""));
		System.out.println();
	}

	public String centerString(int width, String s) {// this function takes string and centers it between certain length
		return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
	}

	protected void startMsg(Process p, int time) {// print the starting message of burst period
		// ternary operator ( variable = condition? true case value : false case value;)
		String start = (p.remainingTime == p.burstTime) ? ": started " : ": continued ";
		System.out.print(p.name + String.format("%-12s", start) + String.format("%2s", time));
	}

	protected void endMsg(Process p, int time) {// print the ending message of burst period
		// ternary operator ( variable = condition? true case value : false case value;)
		String end = (p.remainingTime == 0) ? " | finished " : " | interrupted ";
		System.out.println(String.format("%-15s", end) + String.format("%2s", time));
	}
}
