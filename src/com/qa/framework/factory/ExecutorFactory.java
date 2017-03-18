package com.qa.framework.factory;

import com.qa.framework.bean.TestData;
import com.qa.framework.core.TestXmlData;
import com.qa.framework.mock.IMockServer;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Factory;

import static com.qa.framework.classfinder.ClassHelper.findImplementClass;

/**
 * The type Executor factory.
 */
public class ExecutorFactory {
    private IMockServer mockServer = null;

    /**
     * Before class.
     *
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeClass() throws IllegalAccessException, InstantiationException {
        Class<?> clazz = findImplementClass(IMockServer.class);
        if (clazz != null) {
            mockServer = (IMockServer) clazz.newInstance();
            mockServer.startServer();
            mockServer.settingRules();
        }
    }

    /**
     * After class.
     */
    @AfterSuite(alwaysRun = true)
    public void afterClass() {
        if (mockServer != null) {
            mockServer.stopServer();
        }
    }

    /**
     * Before method.
     *
     * @param context the context
     */
    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        System.out.println("beforeMethod");
    }


    /**
     * Execute object [ ].
     *
     * @param testData   the test data
     * @param url        the url
     * @param httpMethod the http method
     * @return the object [ ]
     */
    @Factory(dataProviderClass = TestXmlData.class, dataProvider = "xmlFactoryData")
    public Object[] execute(TestData testData, String url, String httpMethod) {
        Object[] tests = new Object[1];
        tests[0] = new Executor(testData, url, httpMethod);
        return tests;
    }

}
