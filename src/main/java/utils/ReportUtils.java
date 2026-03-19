package utils;

import org.apache.commons.io.FileUtils;
import reports.ExtentManager;

import java.io.File;

public class ReportUtils {
    public static void copyLatestReport() {
        try {
            String sourceDir = ExtentManager.reportDir;
            String destDir = System.getProperty("user.dir") + "/Reports/ExecutionReport";

            File src = new File(sourceDir);
            File dest = new File(destDir);

            if (dest.exists()) {
                FileUtils.deleteDirectory(dest);
            }

            FileUtils.copyDirectory(src, dest);
            System.out.println("✅ Latest report copied to ExecutionReport");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
