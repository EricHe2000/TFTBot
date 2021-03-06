package com.oaktonridge.eriche.service;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TFTBot {
	final static Logger logger = LoggerFactory.getLogger(TFTBot.class);
	private JFrame frame = new JFrame("Simple GUI");
	public final static String FORMAT = "jpg";
	private List<String> comp;
	private boolean sellFirst = true;
	private boolean sellSecond = true;
	private boolean sellThird = true;
	private HashMap<String, Integer> champCount = new HashMap<>();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss");
	private ScheduledExecutorService service;

	@Autowired
	private Robot robot;

	@Autowired
	private Item item;

	@PostConstruct
	public void init() throws AWTException {
		logger.info("reached init");
		// robot = new Robot();
	}

	public void stop() {
		if (service != null) {
			service.shutdown();
			service = null;
			logger.info("Program has been stopped");
		} else {
			logger.info("Program is not started");
		}
	}

	public void run(String compList) throws AWTException, FileNotFoundException {
		if (service != null) {
			return;
		}
		comp = teamComp(compList);
		if (comp.size() == 0) {
			return;
		}
		// createWindow();
		logger.info("Scripted has started");
		service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					capture();
					reQueue();
					saveImage();
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000, 1000, TimeUnit.MILLISECONDS);

		KeyListener listener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_9) {
					System.exit(0);
				}
			}

			@Override
			public void keyReleased(KeyEvent event) {
			}

			@Override
			public void keyTyped(KeyEvent event) {
			}
		};
		frame.addKeyListener(listener);
	}

	public void click(int x, int y) throws AWTException, InterruptedException {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		TimeUnit.MILLISECONDS.sleep(500);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		// bot.mousePress(InputEvent.BUTTON1_MASK);
		// bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void sellFirst() {
		robot.delay(100); // Click one second
		robot.mouseMove(983, 650); // step 3
		robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_E);
		robot.delay(100);
	}

	public void sellSecond() {
		robot.delay(100); // Click one second
		robot.mouseMove(429, 760); // step 3
		robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_E);
		robot.delay(100);
	}

	private List<String> teamComp(String compList) {
		List<String> comp = new ArrayList<>();
		// Scanner sc = new Scanner(System.in);
		// System.out.println("Enter Team File: ");
		// String compList = sc.nextLine();
		File file = new File("src\\main\\resources\\TeamComps\\" + compList);
		// sc.close();
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				comp.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			logger.error("File not found" + e.getMessage(), e);
		}
		return comp;

	}

	public void capture() throws IOException, AWTException, InterruptedException {
		String folder = "src\\main\\resources\\Captures\\";
		String folderCard = "src\\main\\resources\\Cards\\";
		for (int i = 1; i <= 6; i++) {
			try {
				String fileName = folderCard + i + "." + FORMAT;
				switch (i) {
				case 1:
					captureImage(490, 935, fileName);
					break;
				case 2:
					captureImage(686, 935, fileName);
					break;
				case 3:
					captureImage(882, 935, fileName);
					break;
				case 4:
					captureImage(1078, 935, fileName);
					break;
				case 5:
					captureImage(1274, 935, fileName);
					break;
				case 6:
					captureGold(878, 880, folder + "Gold.jpg");
					break;
				}
			} catch (AWTException | IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
		captureStage(956, 27, folder + "stage.jpg");

		File folderChamps = new File("src\\main\\resources\\Champs");
		File[] listOfFiles = folderChamps.listFiles();

		for (int i = 1; i <= 5; i++) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					if (!comp.contains(file.getName())) {
						continue;
					}
					// TimeUnit.MILLISECONDS.sleep(3000);
					if (sellFirst || sellSecond) {
						BufferedImage stage1 = ImageIO.read(new File(folder + "stage.jpg"));
						BufferedImage stage2 = ImageIO.read(new File(folder + "stage2.jpg"));
						BufferedImage stage3 = ImageIO.read(new File(folder + "stage3.jpg"));
						BufferedImage stage4 = ImageIO.read(new File(folder + "stage4.jpg"));

						double p = getDifferencePercent(stage1, stage2);
						double p1 = getDifferencePercent(stage1, stage3);
						double p2 = getDifferencePercent(stage1, stage4);
						if (p < 6 && sellFirst) {
							sellFirst();
							robot.delay(2000);
							item.item1();
							item.item1();
							item.item2();
							item.item3();
							item.item4();
							sellFirst = false;
						} else if (p1 < 6 && sellSecond) {

							item.item1();
							item.item1();
							item.item2();
							item.item3();
							item.item4();
							levelUp();
							levelUp();
							levelUp();
							levelUp();

							sellSecond = false;
							surrender();
						} else if (p2 < 6 && sellThird) {
							item.item1();
							item.item1();
							item.item2();
							item.item3();
							item.item4();
							sellThird = false;
							levelUp();
							levelUp();
							levelUp();
							levelUp();
							for (int j = 0; j < 15; j++) {
								reRoll();
							}
						}

					}
					BufferedImage img1 = ImageIO.read(new File("src\\main\\resources\\Cards\\" + i + ".jpg"));
					BufferedImage img2 = ImageIO.read(file);
					double p = getDifferencePercent(img1, img2);
					// System.out.println("img1: " + i + ".jpg" + " img2: " + file.getName() + " p:
					// " + p);
					if (p < 6) {
						if (comp.contains(file.getName())) {
							logger.debug(file.getName());
							switch (i) {
							case 1:
								click(500, 950);
								break;
							case 2:
								click(695, 950);
								break;
							case 3:
								click(900, 950);
								break;
							case 4:
								click(1095, 950);
								break;
							case 5:
								click(1283, 950);
								break;
							}
							addToChampCount(file.getName());
							logger.debug("#############################HashMap:" + champCount);

						}
					}
				}
			}
		}
		robot.delay(1000);
		reRoll();
	}

	private void levelUp() {
		robot.keyPress(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_F);
		robot.delay(1000);
	}

	private void addToChampCount(String name) {
		Integer count = champCount.get(name);
		if (count == null) {
			champCount.put(name, 1);
		} else {
			champCount.put(name, count + 1);
		}
	}

	private void reRoll() throws IOException {
		String folder = "src\\main\\resources\\Captures\\";
		BufferedImage img3 = ImageIO.read(new File(folder + "Gold.jpg"));
		// BufferedImage img4 = ImageIO.read(new File("Gold3" + ".jpg"));
		BufferedImage img5 = ImageIO.read(new File(folder + "Gold5" + ".jpg"));
		// double p = getDifferencePercent(img3, img4);
		double p1 = getDifferencePercent(img3, img5);
		// if (p < 5 || p1 < 5) {
		if (p1 < 5) {
			logger.debug("Re-rolling");
			// robot.keyPress(KeyEvent.VK_F);
			// robot.keyRelease(KeyEvent.VK_F);
			// TimeUnit.MILLISECONDS.sleep(1000);
			robot.keyPress(KeyEvent.VK_D);
			robot.keyRelease(KeyEvent.VK_D);
			robot.delay(5000);
		}
	}

	private void captureImage(int x, int y, String fileName) throws AWTException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle captureRect = new Rectangle(x, y, screenSize.width / 10, screenSize.height / 8);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(fileName));
	}

	private void captureGold(int x, int y, String fileName) throws AWTException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle captureRect = new Rectangle(878, 880, screenSize.width / 160, screenSize.height / 32);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(fileName));
	}

	private void captureStage(int x, int y, String fileName) throws AWTException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle captureRect = new Rectangle(x, y, screenSize.width / 170, screenSize.height / 50);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(fileName));
	}

	private double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
		int width = img1.getWidth();
		int height = img1.getHeight();
		// int width2 = img2.getWidth();
		// int height2 = img2.getHeight();
		/*
		 * if (width != width2 || height != height2) { throw new
		 * IllegalArgumentException(String.format(
		 * "Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height,
		 * width2, height2)); }
		 */

		long diff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
			}
		}
		long maxDiff = 3L * 255 * width * height;

		return 100.0 * diff / maxDiff;
	}

	private int pixelDiff(int rgb1, int rgb2) {
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}

	private void exit() throws IOException {
		String folder = "src\\main\\resources\\Queue\\";
		String fileName = "ExitNowCheck" + "." + FORMAT;

		Rectangle captureRect = new Rectangle(775, 520, screenSize.width / 15, screenSize.height / 20); // captures exit
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));

		BufferedImage exit = ImageIO.read(new File(folder + "ExitNow.jpg"));
		BufferedImage exit1 = ImageIO.read(new File(folder + "ExitNowCheck.jpg"));

		double p = getDifferencePercent(exit, exit1);
		if (p < 6) {
			takeFullShot();
			robot.mouseMove(828, 540); // exit
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);

			robot.delay(1000);

			robot.mouseMove(830, 540); // exit
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
		}
	}

	private void saveImage() throws IOException {
		String folder = "src\\main\\resources\\public\\images\\";
		String fileName = "upload." + FORMAT;
		Rectangle captureRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);

		ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));
	}

	private void takeFullShot() throws IOException {
		String folder = "src\\main\\resources\\Games\\";
		LocalDateTime now = LocalDateTime.now();

		String fileName = dtf.format(now) + "." + FORMAT;
		Rectangle captureRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);

		ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));

		// captureRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
		// screenFullImage = robot.createScreenCapture(captureRect);

		// fileName = dtf.format(now) + "." + FORMAT;
		// ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));
		robot.delay(2000);
	}

	private void playAgain() throws IOException, AWTException, InterruptedException {
		String folder = "src\\main\\resources\\Queue\\";
		String fileName = folder + "PlayAgainCheck" + "." + FORMAT;

		Rectangle captureRect = new Rectangle(800, 849, screenSize.width / 15, screenSize.height / 32);// capture
																										// playAgain
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(fileName));

		BufferedImage playAgain = ImageIO.read(new File(folder + "PlayAgain.jpg"));
		BufferedImage findMatch = ImageIO.read(new File(folder + "FindMatch.jpg"));
		BufferedImage findMatchCheck = ImageIO.read(new File(folder + "findMatchCheck.jpg"));
		BufferedImage playAgainCheck = ImageIO.read(new File(folder + "PlayAgainCheck.jpg"));

		robot.delay(1000);

		double p = getDifferencePercent(playAgainCheck, playAgain);
		double p1 = getDifferencePercent(playAgainCheck, findMatch);
		double p2 = getDifferencePercent(playAgainCheck, findMatchCheck);
		if (p < 6) {
			takeFullShot();
			click(865, 860); // play again && findMatch
			robot.delay(5000);
		}
		if (p1 < 6 || p2 < 6) {
			click(865, 860); // play again && findMatch
			robot.delay(5000);
		}
	}

	private void acceptQueue() throws IOException, AWTException, InterruptedException {
		String fileName = "AcceptCheck" + "." + FORMAT;
		String folder = "src\\main\\resources\\Queue\\";
		Rectangle captureRect = new Rectangle(775, 520, screenSize.width / 10, screenSize.height / 20);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));

		BufferedImage accept = ImageIO.read(new File(folder + "Accept.jpg"));
		BufferedImage AcceptCheck = ImageIO.read(new File(folder + "AcceptCheck.jpg"));

		double p = getDifferencePercent(accept, AcceptCheck);

		if (p < 6) {
			click(965, 728);
			sellFirst = true;
			sellSecond = true;
			sellThird = true;
		}
		robot.delay(1000);
	}

	private void skipStats() throws IOException, AWTException, InterruptedException {
		String fileName = "skipStatsCheck" + "." + FORMAT;
		String folder = "src\\main\\resources\\Queue\\";
		Rectangle captureRect = new Rectangle(741, 524, screenSize.width / 10, screenSize.height / 40);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));

		BufferedImage skipStats = ImageIO.read(new File(folder + "skipStats.jpg"));
		BufferedImage skipStatsCheck = ImageIO.read(new File(folder + "skipStatsCheck.jpg"));
		BufferedImage skipStats1 = ImageIO.read(new File(folder + "skipStats1.jpg"));
		double p = getDifferencePercent(skipStats, skipStatsCheck);
		double p1 = getDifferencePercent(skipStats1, skipStatsCheck);
		if (p < 6 || p1 < 6) {
			click(843, 533);
		}
		robot.delay(1000);
	}

	private void play() throws IOException, AWTException, InterruptedException {
		String fileName = "playCheck" + "." + FORMAT;
		String folder = "src\\main\\resources\\Queue\\";
		Rectangle captureRect = new Rectangle(389, 553, screenSize.width / 20, screenSize.height / 40);
		BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
		ImageIO.write(screenFullImage, FORMAT, new File(folder + fileName));

		BufferedImage play = ImageIO.read(new File(folder + "play.jpg"));
		BufferedImage playCheck = ImageIO.read(new File(folder + "playCheck.jpg"));

		double p = getDifferencePercent(play, playCheck);

		if (p < 6) {
			click(452, 562);
		}
		robot.delay(1000);
	}

	private void surrender() throws AWTException, InterruptedException {
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_SLASH);
		robot.keyPress(KeyEvent.VK_F);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(1000);
		click(859, 632);
		click(859, 632);
		click(859, 632);
	}

	private void reQueue() throws IOException, AWTException, InterruptedException {
		exit();
		skipStats();
		play();
		playAgain();
		acceptQueue();
	}

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