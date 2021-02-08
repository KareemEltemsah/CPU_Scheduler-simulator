import java.util.ArrayList;

import javax.swing.LayoutStyle;

public class RR_Scheduler extends Scheduler {
	// ready queue to manipulate processes position after quantum end
	ArrayList<Process> readyQueue = new ArrayList<Process>();
	int contextSwitching;
	int quantum;// the max amount of each CPU burst
	Process previousProcess = null;// a flag for the previous executing process

	public RR_Scheduler(ArrayList<Process> p, int cs, int q) {
		processes = p;
		contextSwitching = cs;
		quantum = q;
	}

	@Override
	public void scheduling() {
		int currentTime = 0;// initialize the time
		System.out.println(centerString(33, "excution order:") + "\n");
		while (!isAllProcessesFinished()) {
			arrangeReadyQueue(currentTime);
			if (readyQueue.size() > 0) {
				Process executingProcess = readyQueue.get(0);// take the first process in the queue
				startMsg(executingProcess, currentTime);
				for (int i = 0; i < quantum; i++) {
					executingProcess.remainingTime--;
					currentTime++;
					if (executingProcess.remainingTime == 0) {// process finished
						executingProcess.isFinished = true;
						executingProcess.finishTime = currentTime;
						break;
					}
				}
				previousProcess = executingProcess;// saving reference for the last working process
				endMsg(executingProcess, currentTime);
				currentTime += contextSwitching;// add the context switching value after every switch
			} else// no processes in the ready queue
				currentTime++;// increment time till new process arrive
		}
		printSchedulingStatistics();
	}

	private void arrangeReadyQueue(int time) {
		for (int i = 0; i < processes.size(); i++) {
			Process p = processes.get(i);
			if (p.arrivalTime <= time && !p.isFinished && !readyQueue.contains(p))
				readyQueue.add(p);
		}
		// if this isn't first CPU burst && previous process still first in the queue
		if (readyQueue.size() > 0 && previousProcess != null && readyQueue.get(0) == previousProcess) {
			readyQueue.remove(previousProcess);// remove previous process from the queue
			if (!previousProcess.isFinished)
				readyQueue.add(previousProcess);// add it in the end of the queue again if it's not finished
		}
	}
}
