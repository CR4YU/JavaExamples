package org.company;

import java.util.List;
import java.util.Random;

public class Consumer extends Thread {

	private static final int MAX_PROCESS_TIME_SEC = 10;

	private List<Object> objList;

	public Consumer(String name, List<Object> objList) {
		setName(name);
		this.objList = objList;
	}

	@Override
	public void run() {
		while(true) {
			Object obj = null;
			synchronized (objList) {
				waitUntilConsumptionPossible();
				obj = objList.remove(0);
				objList.notifyAll();
				logConsumptionMade(obj);
			}
			processObject(obj);
		}
	}

	private void waitUntilConsumptionPossible() {
		while(objList.isEmpty()) {
			try {
				objList.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void processObject(Object obj) {
		//doing something with obj
		waitRandomTime();
	}

	private void logConsumptionMade(Object obj) {
		System.out.println("Consumer " + getName() + " has just consumed object " + obj +
		". Current list size " + objList.size());
	}

	private void waitRandomTime() {
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(MAX_PROCESS_TIME_SEC * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
