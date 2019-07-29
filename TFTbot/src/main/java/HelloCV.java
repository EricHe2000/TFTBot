import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

class MatchingDemo {
	public void run(String inFile, String templateFile, String outFile, int match_method) {
		System.out.println("\nRunning Template Matching");

		Mat img = Imgcodecs.imread(inFile);
		Mat templ = Imgcodecs.imread(templateFile);

		// / Create the result matrix
		int result_cols = img.cols() - templ.cols() + 1;
		int result_rows = img.rows() - templ.rows() + 1;
		Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

		// / Do the Matching and Normalize
		Imgproc.matchTemplate(img, templ, result, match_method);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

		// / Localizing the best match with minMaxLoc
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		Point matchLoc;
		if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
			matchLoc = mmr.minLoc;
		} else {
			matchLoc = mmr.maxLoc;
		}

		double minMatchQuality = 0.9;// with CV_TM_SQDIFF_NORMED you could use 0.1
		if (mmr.maxVal > minMatchQuality) {// with CV_TM_SQDIFF_NORMED use minValue < minMatchQuality
			System.out.println(" accept the match");
		}

		else {
			System.out.println(" reject the match");
		}

		// / Show me what you got
		Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
				new Scalar(0, 255, 0));

		System.out.println("x: " + matchLoc.x + templ.cols() + " y: " + matchLoc.y);

		// Save the visualized detection.
		System.out.println("Writing " + outFile);
		Imgcodecs.imwrite(outFile, img);
	}
}

public class HelloCV {

	public static void main(String[] args) {
		nu.pattern.OpenCV.loadShared();
		new MatchingDemo().run(args[0], args[1], args[2], Imgproc.TM_CCOEFF);
	}

}