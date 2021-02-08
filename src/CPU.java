import java.util.ArrayList;
import java.util.Scanner;

public class CPU {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Which scheduling technique you want to use ?\n" + "1-preemptive Shortest Job First\n"
					+ "2-Round Robin\n" + "3-preemptive Priority Scheduling\n" + "4-Multi level Scheduling");
			int techniqueChoice = scanner.nextInt();
			System.out.println("\nHow many processes you want to excute ?");
			int nOfProcesses = scanner.nextInt();
			ArrayList<Process> processes = readProcesses(nOfProcesses, techniqueChoice);// taking processes information

			Scheduler scheduler = new Scheduler();// using one scheduler to point for any of the 4 type (polymorphism)
			if (techniqueChoice == 1) {
				// preemptive SJF scheduling (SRTF)
				System.out.println("Context switching:");
				int contextSwitching = scanner.nextInt();
				scheduler = new SRTF_Scheduler(processes, contextSwitching);
			} else if (techniqueChoice == 2) {
				// RR scheduling
				System.out.println("Context switching:");
				int contextSwitching = scanner.nextInt();
				System.out.println("Quantum:");
				int Quantum = scanner.nextInt();
				scheduler = new RR_Scheduler(processes, contextSwitching, Quantum);
			} else if (techniqueChoice == 3) {
				// priority scheduling
				scheduler = new Priority_Scheduler(processes);
			} else if (techniqueChoice == 4) {
				// Multilevel scheduling (RR + FCFS)
				System.out.println("Quantum:");
				int Quantum = scanner.nextInt();
				scheduler = new MultiLevel_Scheduler(processes, Quantum);
			} else// invalid choice
				continue;

			scheduler.scheduling();
			System.out.println("try another technique\t1-yes\t0-exit");// asking user if he wants to continue
			if (scanner.nextInt() == 0)
				break;
		}
		scanner.close();
	}

	public static ArrayList<Process> readProcesses(int nOfProcesses, int technique) {
		ArrayList<Process> processes = new ArrayList<Process>();
		System.out.println("\nEnter each process in a line\n" + "name, arrival time, burst time (eg. P1,0,5)\n"
				+ "add priority and queue number if there is");
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < nOfProcesses; i++) {
			String[] temp = scanner.nextLine().split(",");
			int arrivalTime = Integer.parseInt(temp[1]);
			int burstTime = Integer.parseInt(temp[2]);
			Process p = new Process(temp[0], arrivalTime, burstTime);
			if (technique == 3)// adding priority
				p.priority = Integer.parseInt(temp[3]);
			if (technique == 4)// adding queue number for MultiLevel
				p.queueNumber = Integer.parseInt(temp[3]);
			processes.add(p);

		}
		return processes;
	}
}
