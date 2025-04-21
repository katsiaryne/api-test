package in.res.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTest implements IRetryAnalyzer {
    private int retryCount = 0;
    private final int MAX_COUNT = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < MAX_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }
}
