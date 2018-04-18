package com.company;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReaderWriterDemo {

	private static final int NUM_READER_THREADS = 3;

	private SharedData sharedObj = new SharedData();
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		(new ReaderWriterDemo()).runDemo();
	}

	private void runDemo() {
		new Writer(0, sharedObj, lock);

		for(int i=0; i<NUM_READER_THREADS; i++) {
			new Reader(i, sharedObj, lock);
		}
	}
}
