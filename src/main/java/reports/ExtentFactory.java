package reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {
    private ExtentFactory() {

    }
    private static ExtentFactory instance = new ExtentFactory();
    //this method will return instance of current class, we need Cz we created the private constructor. so obj can not be created directly
    public static ExtentFactory getInstance() {
        return instance;
    }
    //ExtentTest object used to log information on report so we need to make this object thread safe
    ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();
    public ExtentTest getExtent() {
        return extent.get();
    }
    //for setting extent test obj into thread local
    public void setExtent(ExtentTest extentObj) {
        extent.set(extentObj);
    }
    public void removeExtentObject() {
        extent.remove();
    }
}
