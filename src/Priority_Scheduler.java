import java.util.ArrayList;

public class Priority_Scheduler extends Scheduler {
	int agingPeriod = 4;// amount of time to make some process higher priority

	public Priority_Scheduler(ArrayList<Process> p) {
		processes = p;
	}

	@Override
	public void scheduling() {
		int currentTime = 0;// initialize the time
		System.out.println(centerString(33, "excution order:") + "\n");
		while (!isAllProcessesFinished()) {
			int optimalProcessIndex = getOptimalProcess(currentTime);
			if (optimalProcessIndex >= 0) {
				Process executingProcess = processes.get(optimalProcessIndex);
				startMsg(executingProcess, currentTime);
				while (executingProcess.remainingTime > 0) {
					executingProcess.remainingTime--;
					currentTime++;
					aging(currentTime);// check if there a process should be aged before the if condition
					// if higher priority process arrived
					if (processes.get(getOptimalProcess(currentTime)) != executingProcess)
						break;
				}
				if (executingProcess.remainingTime == 0) {// process finished
					executingProcess.isFinished = true;
					executingProcess.finishTime = currentTime;
				}
				endMsg(executingProcess, currentTime);
			} else// no optimal process in this time
				currentTime++;// increment time till new process arrive
		}
		printSchedulingStatistics();
	}

	private int getOptimalProcess(int time) {
		int optimalProcess = -1;
		for (int i = 0; i < processes.size(); i++) {
			Process p = processes.get(i);
			if (p.arrivalTime <= time && !p.isFinished
					&& (optimalProcess == -1 || p.priority < processes.get(optimalProcess).priority))
				optimalProcess = i;
		}
		return optimalProcess;
	}

	private void aging(int time) {// this function is to solve starvation problem
		for (int i = 0; i < processes.size(); i++) {
			Process p = processes.get(i);
			// each time process spent *aging period* in the system, it's priority getting
			// higher, (not less than 0) because it's the highest priority
			if ((time - p.arrivalTime) % agingPeriod == 0 && time > p.arrivalTime && p.priority > 0) {
				p.priority--;
			}
		}
	}
}
