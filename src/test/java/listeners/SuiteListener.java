package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.ReportUtils;

public class SuiteListener implements ISuiteListener {
    @Override
    public void onFinish(ISuite suite) {
        // Runs ONLY ONCE after all tests complete
        ReportUtils.copyLatestReport();
    }
}
