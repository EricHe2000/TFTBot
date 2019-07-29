package com.oaktonridge.eriche;

public class Main {
	public static void main(String[] args) {

		TFTBot t = new TFTBot();
		try {
			t.run("wildnoble.txt");
		} catch (Exception e) {
			System.out.println("System Aborted: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
