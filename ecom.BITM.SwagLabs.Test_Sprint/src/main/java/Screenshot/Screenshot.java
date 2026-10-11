//package Screenshot;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//
//
//public class Screenshot {
//	public static void takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
//
//        TakesScreenshot ts = (TakesScreenshot) driver;
//
//        File source = ts.getScreenshotAs(OutputType.FILE);
//
//        File destination = new File("./Screenshot/" + screenshotName + ".png");
//
//        Files.copy(source.toPath(), destination.toPath());
//    }
//
//}

package Screenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	public static void takeScreenshot(WebDriver driver, String screenshotName) throws IOException {

		// Capture screenshot
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Create folder if it does not exist
		File folder = new File("./Screenshot");

		if (!folder.exists()) {
			folder.mkdirs();
		}

		// Destination file
		File destination = new File(folder, screenshotName + ".png");

		// Copy screenshot and overwrite existing file
		Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

		System.out.println("Screenshot saved: " + destination.getAbsolutePath());
	}
}
