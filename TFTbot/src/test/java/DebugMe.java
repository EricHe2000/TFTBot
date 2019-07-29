import java.util.ArrayList;
import java.util.Random;

public class DebugMe {

	public static void main(String[] args) {
		int argsNeeded = 29;
		if (args.length < argsNeeded) {
			String[] temp = new String[argsNeeded];
			for (int i = 0; i < argsNeeded; i++) {
				if (i < args.length) {
					temp[i] = args[i];
				} else {
					temp[i] = "";
				}
			}
			args = temp;
		}

		String userID = args[0];
		int hash = Math.abs(userID.hashCode());
		hash %= 20000;

		int score = 0;
		int[] points = { 5, 5, 5, 6, 6, 6, 7, 7, 8 };
		int pi = 0;
		int maxScore = 0;
		for (int p : points) {
			maxScore += p;
		}
		boolean lost = false;
		try {
			System.out.println("Let the games... BEGIN!");

			System.out.println("Phase One, let's have some fun!");
			phase1(hash, args, 1);
			score += points[0];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("Good for you, time for phase two!");
			}
			phase2(hash, args, 4);
			score += points[1];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("O-M-G, here's phase three!");
			}
			phase3(hash, args, 7);
			score += points[2];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("time for more, here's phase four.");
			}
			phase4(hash, args, 11);
			score += points[3];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("still alive? Here's phase five.");
			}
			phase5(hash, args, 15);
			score += points[4];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("watch out, here comes a wall of bricks! It's time for you to solve phase six.");
			}
			phase6(hash, args, 17);
			score += points[5];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("next it's phase eleven! oops, seven.");
			}
			phase7(hash, args, 21);
			score += points[6];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("youre doing great - now try phase eight!");
			}
			phase8(hash, args, 24);
			score += points[7];
		} catch (Exception e) {
			lost = true;
		}
		try {
			if (!lost) {
				System.out.println("finally, the finish line - I hope that you can solve phase nine!");
			}
			phase9(hash, args, 27);
			score += points[8];
		} catch (Exception e) {
			lost = true;
		}
		if (!lost) {
			System.out.println("Hey, neat - the task's complete!");
		} else {
			System.out.println("oops! Game Over... >:( ");
		}
		System.out.printf("\n  * Score: %s/%s pts *\n", score, maxScore);
	}

	// 3 args
	public static void phase1(int hash, String[] args, int argStart) {
		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		int c = Integer.parseInt(args[argStart + 2]);

		a += hash % 40;

		if (a < b && b > c && a < c) {
			return;
		}
		failure("Double debugger burger, order up!");

	}

	// 3 args
	public static void phase2(int hash, String[] args, int argStart) {
		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		int c = Integer.parseInt(args[argStart + 2]);

		a += hash % 26;

		int v = a + b;
		v *= a;
		v /= b;
		v += 14;
		if (c != v) {
			failure("these are not the ints you're searching for...");
		}
	}

	// 4 args
	public static void phase3(int hash, String[] args, int argStart) {
		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		int c = Integer.parseInt(args[argStart + 2]);
		int d = Integer.parseInt(args[argStart + 3]);

		if (hash % 13 > 4) {
			c += hash % 13;
		} else {
			a *= hash % 5 + 1;
		}

		int temp = 0;
		do {
			temp += a;
			temp *= b;
		} while (c-- > 0);
		if (temp != d)
			failure("count around, products abound; what's that there - an arrow? uh-oh...");
	}

	// 4 args
	public static void phase4(int hash, String[] args, int argStart) {
		String s = args[argStart + 0];
		int a = Integer.parseInt(args[argStart + 1]);
		int b = Integer.parseInt(args[argStart + 2]);
		int c = Integer.parseInt(args[argStart + 3]);

		String temp1 = s.substring(a, b);
		String temp2 = s.substring(b, c);
		int temp3 = 0;
		while (temp3 < temp1.length() && temp3 < temp2.length()) {
			if (temp1.charAt(temp3) != temp2.charAt(temp3)) {
				break;
			}
			temp3++;
		}
		if (temp3 != 6)
			failure("over here and over there, I need a certain kind of pair...");
	}

	// helperfor phase 5.
	public static String collatzString(int n) {
		String s = "[";
		while (n != 1) {
			s += n + ", ";
			if (n % 2 == 0) {
				n /= 2;
			} else {
				n = n * 3 + 1;
			}
		}
		s += "1]";
		return s;
	}

	// 2 args
	public static void phase5(int hash, String[] args, int argStart) {
		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		String s = collatzString(a);
		if (b != s.length()) {
			failure("collecting strings and lengths of things in one big batch; so what should match?");
		}
	}

	// 4 args
	public static void phase6(int hash, String[] args, int argStart) {
		// TODO: remove
		// use hash and % to look into a many-way switch;
		// each one calls another function with different args.
		// those args are used to loop around code with some
		// bizarre if-conditions that modify a target or check
		// the target, figuring out when to jump ship. Some
		// branches failure() out. Students need to change one
		// of their inputs to guarantee that an escapable path
		// is chosen (they should be able to guarantee it as
		// all those function-calls' args are variants on their
		// provided CLI args).

		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		int c = Integer.parseInt(args[argStart + 2]);
		int v = 0;
		int temp = 6;
		switch (a + hash % 11) {
		case 1:
			v = helper3(hash, a + 12, b, temp++);
			break;
		case 2:
			v = helper3(hash, a * 20, b - a, 5);
			break;
		case 3:
			v = helper3(hash, a - 310, a - b, 5);
			break;
		case 4:
			v = helper3(hash, (int) Math.pow(a, 2), b, 5);
			break;
		case 5:
			v = helper3(hash, (int) Math.pow(2, a), b, 5);
			break;
		case 6:
			v = helper3(hash, b, a, temp--);
			break;
		case 7:
			v = helper3(hash, a, b + a, temp * 2);
			break;
		case 8:
			v = helper3(hash, a + temp, b - temp, temp % temp);
			break;
		case 9:
		case 10:
		case 11:
			v = helper3(hash, a, b, temp + 10);
			break;
		default:
			v = -1;
		}
		if (!secretMessages[c].equals(args[v])) {
			failure("wrong message!");
		}
	}

	private static String[] secretMessages = { "a closed mouth gathers no feet", "a waist is a terrible thing to mind",
			"out of my mind - back in five minutes", "the harder I work, the luckier I become",
			"with great power comes a big electricity bill", "time flies like an arrow; fruit flies like a banana",
			"five exclamation marks, the sure sign of an insane mind",
			"I do not like green eggs and ham, I do not like them, Sam-I-am.",
			"CS majors need to take ENGH302*N*, not just any section of ENGH302",
			"+++Divide By Cucumber Error. Please Reinstall Universe And Reboot +++",
			"there are 10 kinds of people in the world, those who understand binary and those who don't" };

	public static int helper3(int hash, int x, int y, int z) {
		// TODO: remove.
		// goal: return a number from 10 to 20.

		for (int j = 0; j < y; j++) {
			if (prime(j)) {
				z += 1;
			}
		}
		if (z < 10 || z > 20) {
			failure("uh-oh!");
		}
		return z;
	}

	public static boolean prime(int n) {
		if (n < 2)
			return false;
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/*
	 * TODO: remove. give them a recursive function, like fibonacci, and they need
	 * an input that drives a match to another value (either another argument or
	 * just something hardcoded based on their hash)
	 *
	 */
	public static int sumRange(int start, int stop) {
		if (start > stop) {
			return 0;
		}
		return start + (sumRange(start + 1, stop));
	}

	// 3 args
	public static void phase7(int hash, String[] args, int argStart) {
		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		int c = Integer.parseInt(args[argStart + 2]);
		int d = sumRange(a, b);
		if (c != d) {
			failure("round and round and round she goes, but where she stops, nobody knows!");
		}
	}

	// With all this randomness, surely you want to step through with the
	// debugger, yes?

	// 3 args
	public static void phase8(int hash, String[] args, int argStart) {
		Random r = new Random(hash);
		int a = Integer.parseInt(args[argStart + 0]);
		int b = Integer.parseInt(args[argStart + 1]);
		int c = Integer.parseInt(args[argStart + 2]);
		int s = 0;
		for (int i = 0; i < a; i += b) {
			if (r.nextInt(hash) % c == 0) {
				s += 1;
			}
		}
		if (s != 10) {
			failure("level one, as yet undone! " + s);
		}
	}

	// Generating so much data, how can you find a reliable answer?
	// Remember, you can't change the source code for your submitted
	// solution...

	// 2 args
	public static void phase9(int hash, String[] args, int argStart) {
		createWaldoPage(args, argStart);
	}

	private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	// hmmm, what happens if this is true??? not that you can change it
	// for the final submission...
	public static final boolean DEBUG = false;

	public static void debug(Object msg) {
		if (DEBUG) {
			System.out.print("" + msg);
		}
	}

	public static void debugln(Object msg) {
		debug(msg + "\n");
	}

	public static String[] createWaldoPage(String[] args, int argStart) {
		int hash = Math.abs(args[0].hashCode());
		debugln("hash = " + hash);
		int n = Integer.parseInt(args[argStart + 0]);
		int maxChanges = Integer.parseInt(args[argStart + 1]);
		debug(String.format("n=%s, maxChanges=%s\n", n, maxChanges));
		String[] crowd = new String[n];
		for (int i = 0; i < n; i++) {
			crowd[i] = mutate("waldo", maxChanges, hash++);
		}
		checkWaldoPage(crowd);
		return crowd;
	}

	public static void checkWaldoPage(String[] crowd) {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < crowd.length; i++) {
			debugln("c[i] = " + crowd[i]);
			if (crowd[i].equals("waldo")) {
				debugln(i);
				indexes.add(i);
			}
		}
		if (indexes.size() < 1)
			DebugMe.failure("couldn't find waldo!");
		else if (indexes.size() > 1)
			DebugMe.failure("highlander rule: there can be only one (waldo)! " + indexes.size());
	}

	public static String mutate(String s, int n, int seed) {
		debug(String.format("mutation(%s#): %s (hash=%s)\n", n, s, seed));
		Random r = new Random(seed);
		for (int i = 0; i < n; i++) {
			s = attemptChange(s, seed++);
		}
		debugln(" --> " + s);
		return s;
	}

	public static String attemptChange(String s, int seed) {
		debug(String.format("\tattempt(seed=%d): %s\n", seed, s));
		Random r = new Random(seed);
		int i = r.nextInt(s.length() + 5);

		switch (r.nextInt(3)) {
		case 0:
			try {
				debug(String.format("\t\t%s(%s)%s\n", s.substring(0, i), letters.charAt(i), s.substring(i)));
				s = String.format("%s%s%s", s.substring(0, i), letters.charAt(i), s.substring(i));
			} catch (StringIndexOutOfBoundsException e) {
			}
			break;
		case 1:
			try {
				debug(String.format("\t\t%s(%s/%s)%s\n", s.substring(0, i), letters.charAt(i), s.charAt(i),
						s.substring(i + 1)));
				s = String.format("%s%s%s", s.substring(0, i), letters.charAt(i), s.substring(i + 1));
			} catch (StringIndexOutOfBoundsException e) {
			}
			break;
		case 2:
			try {
				s = s.substring(0, i) + s.substring(i + 1);
			} catch (StringIndexOutOfBoundsException e) {
			}
			break;
		default:
			debugln("\n\n\n\t   --> DEFAULT!\n\n\n");
		}
		return s;
	}

	// any time the program has wandered into the wrong spot, we call this
	// to indicate the phase was not successfully passed.
	public static void failure(String msg) {
		System.out.println("\n\t" + msg + "\n");
		throw new RuntimeException("Failure! " + msg);
	}
}
