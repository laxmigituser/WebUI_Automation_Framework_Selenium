package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.ConfigReader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    static ExtentReports extent;
    public static String reportDir;
    public static ExtentReports setUpExtentReport() throws Exception {
//        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
//        Date date = new Date();
//        String actualdate = format.format(date);
//        String reportpath = System.getProperty("user.dir") + "/Reports/ExecutionReport_" + actualdate + ".html";

        //Jenkins Integration friendly report
        Date date = new Date();
        // Date folder (e.g., 18-03-2026)
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        // Time folder (e.g., 10-30-00)
        String currentTime = new SimpleDateFormat("HH-mm-ss").format(date);
        // Base report directory
        String baseDir = System.getProperty("user.dir") + "/Reports/";
        // Final folder path
        reportDir = baseDir + currentDate + "/" + currentTime;

        // Create directories if not exist
        File dir = new File(reportDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Final report path
        String reportpath = reportDir + "/index.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportpath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        sparkReporter.config().setDocumentTitle("DocumentTitle");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName("ReportName");
        sparkReporter.config().setOfflineMode(true); // for jenkins

        extent.setSystemInfo("Executed on Environment: ", ConfigReader.getPropertyValueByKey("url"));
//        extent.setSystemInfo("Executed on Browser: ", ConfigReader.getPropertyValueByKey("browser"));
        extent.setSystemInfo("Executed on OS: ", System.getProperty("os.name"));
        extent.setSystemInfo("Executed By User: ", System.getProperty("user.name"));


        return extent;

    }
}
