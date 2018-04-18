package com.company;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;

public class Reader implements Runnable {

	public static final int MAX_WAIT_TIME_SEC = 3;

	private SharedData sharedObj;
	private ReadWriteLock lock;
	private int id;

	public Reader(int id, SharedData sharedObj, ReadWriteLock lock) {
		this.id = id;
		this.sharedObj = sharedObj;
		this.lock = lock;

		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true) {
			lock.readLock().lock();
			try {
				int newValue = sharedObj.read();
				logReadMade(newValue);
			} finally {
				lock.readLock().unlock();
			}
			waitRandomTime();
		}
	}

	private void logReadMade(int value) {
		System.out.println("Reader thread " + id + " has read value " + value);
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
