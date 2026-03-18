package retry;

import config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {
    int counter = 1;
    int retryMaxLimit = 0;

    public void getRetryCount(){
        try{
            retryMaxLimit = Integer.parseInt(ConfigReader.getPropertyValueByKey("retryCount"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean retry(ITestResult result){
        if(counter<retryMaxLimit){
            counter++;
            return true;
        }
        return false;
    }
}
