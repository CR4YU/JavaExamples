package org.company;

import java.util.List;
import java.util.Random;

public class Producer extends Thread {

	private static final int MAX_WAIT_TIME_SEC = 3;
	private static final int LIST_CAPACITY = 10;

	private boolean done = false;
	private List<Object> objList;

	public Producer(String name, List<Object> objList) {
		setName(name);
		this.objList = objList;
	}

	@Override
	public void run() {
		while(!done) {
			Object newOBj = requestNewObject();
			synchronized (objList) {
				waitUntilProductionPossible();
				objList.add(newOBj);
				objList.notifyAll();
				logNewObjectProduced(newOBj);
			}
		}
	}

	private void waitUntilProductionPossible() {
		while(objList.size() == LIST_CAPACITY) {
			try {
				objList.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setDone() {
		done = true;
	}

	private Object requestNewObject() {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<5; i++) {
			s.append(getRandomChar());
		}
		waitRandomTime();
		return s.toString();
	}

	private char getRandomChar() {
		return (char)new Random().nextInt(26 + 'a');
	}

	private void logNewObjectProduced(Object obj) {
		System.out.println("Producer " + getName() + " has just created new object " + obj +
		". Current list size: " + objList.size());
	}

	private void waitRandomTime() {
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(MAX_WAIT_TIME_SEC * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
