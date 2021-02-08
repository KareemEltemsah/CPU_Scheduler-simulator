import java.util.ArrayList;

import javax.print.attribute.standard.Sides;

public class MultiLevel_Scheduler extends Scheduler {
	// this multilevel have 2 queue (1-RR 2-FCFS)
	ArrayList<Process> RR_readyQueue = new ArrayList<Process>();
	ArrayList<Process> FCFS_readyQueue = new ArrayList<Process>();
	Process lastRRProcess = null;// a flag for the last executing process from RR queue
	int quantum;

	public MultiLevel_Scheduler(ArrayList<Process> p, int q) {
		processes = p;
		quantum = q;
	}

	@Override
	public void scheduling() {
		int currentTime = 0;// initialize the time
		System.out.println(centerString(33, "excution order:") + "\n");
		while (!isAllProcessesFinished()) {
			arrangeReadyQueue(currentTime);
			Process executingProcess = null;
			if (RR_readyQueue.size() > 0) {// if there processes in the RR queue
				executingProcess = RR_readyQueue.get(0);
				startMsg(executingProcess, currentTime);
				for (int i = 0; i < quantum; i++) {
					executingProcess.remainingTime--;
					currentTime++;
					if (executingProcess.remainingTime == 0) {
						break;
					}
				}
				lastRRProcess = executingProcess;
			} else if (FCFS_readyQueue.size() > 0) {// if there processes in the 2nd queue (FCFS)
				executingProcess = FCFS_readyQueue.get(0);
				startMsg(executingProcess, currentTime);
				while (executingProcess.remainingTime > 0) {
					executingProcess.remainingTime--;
					currentTime++;
					arrangeReadyQueue(currentTime);
					if (RR_readyQueue.size() > 0)// if process arrived in the first queue
						break;
				}
			} else// no processes in both queues
				currentTime++;// increment time till new process arrive

			if (executingProcess != null) {
				if (executingProcess.remainingTime == 0) {
					executingProcess.isFinished = true;
					executingProcess.finishTime = currentTime;
				}
				endMsg(executingProcess, currentTime);
			}
		}
		printSchedulingStatistics();
	}

	private void arrangeReadyQueue(int time) {
		for (int i = 0; i < processes.size(); i++) {
			Process p = processes.get(i);
			if (p.arrivalTime <= time && !p.isFinished) {
				// add the process to it's suitable queue if it's not in the queue already
				if (p.queueNumber == 1 && !RR_readyQueue.contains(p))
					RR_readyQueue.add(p);
				else if (p.queueNumber == 2 && !FCFS_readyQueue.contains(p))
					FCFS_readyQueue.add(p);
			}
		}
		// same as RR_Scheduler we need to remove last RR process from the queue and add
		// it in the end if it's not finished
		if (RR_readyQueue.size() > 0 && lastRRProcess != null && RR_readyQueue.get(0) == lastRRProcess) {
			RR_readyQueue.remove(lastRRProcess);
			if (!lastRRProcess.isFinished)
				RR_readyQueue.add(lastRRProcess);
		}
		if (FCFS_readyQueue.size() > 0) {// remove completed processes from the queue
			while (FCFS_readyQueue.get(0).isFinished)
				FCFS_readyQueue.remove(0);
		}
	}
}
