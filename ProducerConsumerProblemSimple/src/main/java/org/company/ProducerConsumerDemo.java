package org.company;

import java.util.LinkedList;
import java.util.List;

public class ProducerConsumerDemo {

	private static final int NUM_PRODUCER_THREADS = 2;
	private static final int NUM_CONSUMER_THREADS = 5;

	private List<Object> objList = new LinkedList<>();

	public static void main(String[] args) {
		new ProducerConsumerDemo().runDemo();
	}

	private void runDemo() {
		for(int i=0; i<NUM_PRODUCER_THREADS; i++) {
			new Producer("ProdThread_" + i, objList).start();

		}
		for(int i=0; i<NUM_CONSUMER_THREADS; i++) {
			new Consumer("ConsThread_" + i, objList).start();
		}

	}
}
