import java.util.ArrayList;

public class SRTF_Scheduler extends Scheduler {
	int contextSwitching;

	public SRTF_Scheduler(ArrayList<Process> p, int cs) {
		processes = p;
		contextSwitching = cs;
	}

	@Override
	public void scheduling() {
		int currentTime = 0;// initialize the time
		System.out.println(centerString(33, "excution order:") + "\n");
		while (!isAllProcessesFinished()) {
			int optimalProcessIndex = getOptimalProcess(currentTime);
			if (optimalProcessIndex >= 0) {// there is optimal process
				Process executingProcess = processes.get(optimalProcessIndex);
				startMsg(executingProcess, currentTime);
				while (executingProcess.remainingTime > 0) {
					executingProcess.remainingTime--;
					currentTime++;
					if (processes.get(getOptimalProcess(currentTime)) != executingProcess)// if shorter process arrived
						break;
				}
				if (executingProcess.remainingTime == 0) {// process finished
					executingProcess.isFinished = true;
					executingProcess.finishTime = currentTime;
				}
				endMsg(executingProcess, currentTime);
				currentTime += contextSwitching;// add the context switching value after every switch
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
					&& (optimalProcess == -1 || p.remainingTime < processes.get(optimalProcess).remainingTime))
				optimalProcess = i;
		}
		return optimalProcess;
	}
}
