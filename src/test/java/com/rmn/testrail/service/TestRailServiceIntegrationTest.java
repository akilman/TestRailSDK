package com.rmn.testrail.service;

import com.rmn.testrail.entity.*;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestRailServiceIntegrationTest {
    private static Logger log = LoggerFactory.getLogger(TestRailServiceIntegrationTest.class.getSimpleName());

    /**
     * Test the getTestSuites (plural) API call
     */
    @Test
    public void testGetTestSuites() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);
        Assume.assumeTrue(TestIntegrationSuite.testSuite != null);

        List<TestSuite> suites = TestIntegrationSuite.project.getTestSuites();
        Assert.assertNotNull(suites);
        for (TestSuite testSuite: suites) {
            if (!testSuite.getId().equals(TestIntegrationSuite.testSuite.getId())) {
                continue;
            }
            Assert.assertEquals(String.format("The Test Suite name gathered in list context should match the one gathered from single context: [%s] == [%s]",
                TestIntegrationSuite.testSuite.getName(), testSuite.getName()),
                TestIntegrationSuite.testSuite.getName(), testSuite.getName());
            break;
        }
    }

    /**
     * Test the getTestPlans (plural) API call
     */
    @Test
    public void testGetTestPlans() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);
        Assume.assumeTrue(TestIntegrationSuite.testPlan != null);

        List<TestPlan> plans = TestIntegrationSuite.service.getTestPlans(TestIntegrationSuite.project.getId());
        Assert.assertNotNull("The TestPlans should not be null", plans);
        for (TestPlan testPlan: plans) {
            if (!testPlan.getId().equals(TestIntegrationSuite.testPlan.getId())) {
                continue;
            }
            log.debug("TestPlan: {}", testPlan.getName());
            break;
        }
    }

    /**
     * Test the getTestRuns (plural) call
     */
    @Test
    public void testGetTestRuns() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);
        Assume.assumeTrue(TestIntegrationSuite.testRun != null);

        List<TestRun> runs = TestIntegrationSuite.service.getTestRuns(TestIntegrationSuite.project.getId());
        Assert.assertNotNull("The TestRuns should not be null", runs);
        for (TestRun testRun: runs) {
            if (!testRun.getId().equals(TestIntegrationSuite.testRun.getId())) {
                continue;
            }
            log.debug("TestRun: {}", testRun.getName());
            break;
        }
    }

    /**
     * Test the getTestPlan (singular) and TestPlan.getEntries() method
     */
    @Test
    public void testGetTestPlanEntries() {
        Assume.assumeTrue(TestIntegrationSuite.testPlan != null);

        TestPlan plan = TestIntegrationSuite.service.getTestPlan(TestIntegrationSuite.testPlan.getId());
        Assert.assertNotNull("The TestPlan should not be null", plan);
        Assert.assertEquals("The TestPlan should be the same in singular context as in plural", TestIntegrationSuite.testPlan.getId(), plan.getId());
        Assert.assertEquals("The TestPlan name should match the name from the init data", TestIntegrationSuite.testPlan.getName(), plan.getName());
        Assert.assertEquals("The TestPlan Entries should match the Entries from the init data", TestIntegrationSuite.testPlan.getEntries().get(0).getId(), plan.getEntries().get(0).getId());
    }

    /**
     * Test the getTestPlan (singular) method
     */
    @Test
    public void testGetTestRunsFromPlan() {
        Assume.assumeTrue(TestIntegrationSuite.testPlan != null);
        Assume.assumeTrue(TestIntegrationSuite.testRun != null);

        List<TestRun> runs = TestIntegrationSuite.testPlan.getTestRuns();
        Assert.assertNotNull("The TestRun should net be null", runs);
        Assert.assertTrue("The list of TestRuns should be non-zero", runs.size() > 0);

        for (TestRun run: runs) {
            if (!run.getId().equals(TestIntegrationSuite.testRun.getId())) {
                continue;
            }
            log.debug("TestRun: {}", run.getName());
            break;
        }
    }

    /**
     * Test the getTestCases (plural) method
     */
    @Test
    public void testGetTestCases() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);
        Assume.assumeTrue(TestIntegrationSuite.testSuite != null);
        Assume.assumeTrue(TestIntegrationSuite.testCase != null);

        List<TestCase> cases = TestIntegrationSuite.service.getTestCases(TestIntegrationSuite.project.getId(), TestIntegrationSuite.testSuite.getId());
        Assert.assertNotNull("The list of TestCases should not be null", cases);
        Assert.assertEquals("The TestCase should match the init data", TestIntegrationSuite.testCase.getId(), cases.get(0).getId());
    }

    /**
     * Test the getTests (plural) method from a TestRun
     */
    @Test
    public void testGetTestInstancesFromTestRun() {
        Assume.assumeTrue(TestIntegrationSuite.testRun != null);

        List<TestInstance> testInstances = TestIntegrationSuite.testRun.getTests();
        Assert.assertNotNull("The TestInstance should not be null", testInstances);
    }

    /**
     * Test the getTestCases (plural) from a section
     */
    @Test
    public void testGetTestCasesBySection() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);
        Assume.assumeTrue(TestIntegrationSuite.testSuite != null);
        Assume.assumeTrue(TestIntegrationSuite.section != null);

        List<TestCase> cases = TestIntegrationSuite.service.getTestCases(TestIntegrationSuite.project.getId(), TestIntegrationSuite.testSuite.getId(), TestIntegrationSuite.section.getId());
        Assert.assertNotNull("The TestCases should not be null", cases);
    }

    /**
     * Test the getTestCase (singular) method
     */
    @Test
    public void testGetTestCase() {
        Assume.assumeTrue(TestIntegrationSuite.testCase != null);

        TestCase singleTestCase = TestIntegrationSuite.service.getTestCase(TestIntegrationSuite.testCase.getId());
        Assert.assertNotNull("The TestCases should not be null", singleTestCase);
    }

    /**
     * Test the getTestCases(projectID, suiteID)
     */
    @Test
    public void testGetTestCaseByTitle() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);
        Assume.assumeTrue(TestIntegrationSuite.testSuite != null);

        List<TestCase> cases = TestIntegrationSuite.service.getTestCases(TestIntegrationSuite.project.getId(), TestIntegrationSuite.testSuite.getId());
        Assert.assertNotNull(cases);
        Assert.assertTrue(cases.size() > 0);
    }

    /**
     * Test the TestSuite.getTestCases() method
     */
    @Test
    public void testGetTestCaseFromSuite() {
        Assume.assumeTrue(TestIntegrationSuite.project != null);

        TestCase testCase = TestIntegrationSuite.project.getTestSuites().get(0).getTestCases().get(0);
        Assert.assertNotNull(testCase);
    }

    /**
     * Test the getTestResult() (singular) method
     */
    @Test
    public void testGetMostRecentResult() {
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResult testResult = TestIntegrationSuite.service.getTestResult(TestIntegrationSuite.testInstance.getId());
        Assert.assertNotNull(testResult);
    }

    /**
     * This test ensures that the service always returns the most recent test result in the 0-index of the tests (more
     * to let us know if this part of the TestRails API has changed in any way
     */
    @Test
    @Ignore
    public void testTestResultsReturnLatestToEarliest() {
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        List<TestResult> testResults = TestIntegrationSuite.service.getTestResults(TestIntegrationSuite.testInstance.getId(), 0);
        Assert.assertNotNull(testResults);
        TestResult currentTestResult = testResults.get(0);
        for (int index = 1; index < testResults.size(); index++) {
            log.debug(String.format("Ensuring Created_on date between TestResult [%d] and [%d]: %d > %d", currentTestResult.getId(), testResults.get(index).getId(), currentTestResult.getCreatedOn(), testResults.get(index).getCreatedOn()));
            Assert.assertTrue(String.format("The current test result [%s] was entered BEFORE the previous one [%s]--the results are not being returned most-recent-first!", currentTestResult.getCreatedOn(), testResults.get(index).getCreatedOn()), currentTestResult.getCreatedOn() > testResults.get(index).getCreatedOn());
            currentTestResult = testResults.get(index);
        }
    }

    /**
     * Test the getTestResults (plural) method
     */
    @Test
    public void testGetResults() {
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        List<TestResult> testResults = TestIntegrationSuite.service.getTestResults(TestIntegrationSuite.testInstance.getId(), 5);
        Assert.assertNotNull(testResults);
        Assert.assertTrue(testResults.size() <= 5);
    }

    @Test
    public void testAddTestCaseResultPass() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResult result = new TestResult();
        result.setTestId(TestIntegrationSuite.testInstance.getId());
        result.setVerdict("Passed");
        result.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result.setComment("PASS result worked!!");
        TestIntegrationSuite.service.addTestResult(TestIntegrationSuite.testInstance.getId(), result);
    }

    @Test
    public void testAddTestCaseResultsList() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResults results = new TestResults();

        TestResult result1 = new TestResult();
        result1.setVerdict("Passed");
        result1.setTestId(TestIntegrationSuite.testInstance.getId());
        result1.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result1.setComment("PASS result worked!!");
        results.addResult(result1);

        TestResult result2  = new TestResult();
        result2.setTestId(TestIntegrationSuite.testInstance.getId());
        result2.setVerdict("Failed");
        result2.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result2.setComment("FAIL result worked!!");
        results.addResult(result2);

        TestResult result3  = new TestResult();
        result3.setTestId(TestIntegrationSuite.testInstance.getId());
        result3.setVerdict("Blocked");
        result3.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result3.setComment("Blocked result worked!!");
        results.addResult(result3);

        TestIntegrationSuite.service.addTestResults(TestIntegrationSuite.testRun.getId(), results);
    }

    @Test
    public void testAddTestCaseResultFail() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResult result = new TestResult();
        result.setTestId(TestIntegrationSuite.testInstance.getId());
        result.setVerdict("Failed");
        result.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result.setComment("FAIL result worked!!");
        TestIntegrationSuite.service.addTestResult(TestIntegrationSuite.testInstance.getId(), result);
    }

    @Test
    public void testAddTestCaseResultBlocked() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResult result = new TestResult();
        result.setTestId(TestIntegrationSuite.testInstance.getId());
        result.setVerdict("Blocked");
        result.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result.setComment("BLOCKED result worked!!");
        TestIntegrationSuite.service.addTestResult(TestIntegrationSuite.testInstance.getId(), result);
    }

    @Test
    public void testAddTestCaseResultUntested() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResult result = new TestResult();
        result.setTestId(TestIntegrationSuite.testInstance.getId());
        result.setVerdict("Untested");
        result.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result.setComment("UNTESTED result worked!!");
        TestIntegrationSuite.service.addTestResult(TestIntegrationSuite.testInstance.getId(), result);
    }

    @Test
    public void testAddTestCaseResultRetest() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestResult result = new TestResult();
        result.setTestId(TestIntegrationSuite.testInstance.getId());
        result.setVerdict("Retest");
        result.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result.setComment("RETEST result worked!!");
        TestIntegrationSuite.service.addTestResult(TestIntegrationSuite.testInstance.getId(), result);
    }

    @Test
    public void testAddCustomTestCaseStatus() {
        Assume.assumeTrue(TestIntegrationSuite.destructiveTestsOk);
        Assume.assumeTrue(TestIntegrationSuite.testInstance != null);

        TestStatus.addStatus("Skipped", 6);

        TestResult result = new TestResult();
        result.setTestId(TestIntegrationSuite.testInstance.getId());
        result.setVerdict("Skipped");
        result.setAssignedtoId(TestIntegrationSuite.assignedToId);
        result.setComment("SKIPPED result worked!!");
        TestIntegrationSuite.service.addTestResult(TestIntegrationSuite.testInstance.getId(), result);
    }
}
