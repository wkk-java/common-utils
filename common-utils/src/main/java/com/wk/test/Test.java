package com.wk.test;

import java.util.TimerTask;

public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("1111");
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				System.out.println("1111000");

			}
		};
	}
}
