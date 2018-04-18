package com.company;

public class SharedData {

	private int sharedValue = 0;

	synchronized void write(int newValue) {
		sharedValue = newValue;
	}

	synchronized int read() {
		return sharedValue;
	}
}
