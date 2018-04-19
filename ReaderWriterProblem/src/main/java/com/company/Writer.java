package com.company;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;

public class Writer implements Runnable {

	private static final int MAX_WAIT_TIME_SEC = 3;
	private static final int MAX_VALUE_TO_GENERATE = 1000;

	private SharedData sharedObj;
	private ReadWriteLock lock;
	private int id;

	public Writer(int id, SharedData sharedObj, ReadWriteLock lock) {
		this.id = id;
		this.sharedObj = sharedObj;
		this.lock = lock;

		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true) {
			lock.writeLock().lock();
			try {
				int newValue = generateValue();
				sharedObj.write(newValue);
				logWriteMade(newValue);
			} finally {
				lock.writeLock().unlock();
			}
			waitRandomTime();
		}
	}

	private void logWriteMade(int value) {
		System.out.println("Writer thread " + id + " has written value " + value);
	}

	private void waitRandomTime() {
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(MAX_WAIT_TIME_SEC * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int generateValue() {
		return new Random().nextInt(MAX_VALUE_TO_GENERATE);
	}
}
