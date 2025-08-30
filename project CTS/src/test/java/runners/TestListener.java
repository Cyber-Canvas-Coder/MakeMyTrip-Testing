package runners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.ExtentManager;

public class TestListener implements ITestListener {
    private static ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test passed");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test failed: " + result.getThrowable());
    }
    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}
