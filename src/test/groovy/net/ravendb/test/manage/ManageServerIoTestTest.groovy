package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerIoTestPage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerIoTestTest extends TestBase {

    /**
     * User can perform disk io test.
     * @Step Navigate to Manage Resources page.
     * @Step Click IO Test Info link.
     * @verification User can perform io test.
     */
    @Test(groups="Smoke")
    void canRunDiskIOTest() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.ioTestLink.click()
        waitFor { at ManageServerIoTestPage }

        dirLocationInput { $("input#path") }
        fileSizeInput = "10"
        chooseOption(testTypeButton, ManageServerIoTestPage.TEST_TYPE_SIMPLE)
        chooseOption(operationTypeButton, ManageServerIoTestPage.OPERATION_TYPE_READ_AND_WRITE)
        chooseOption(bufferingButton, ManageServerIoTestPage.BUFFERING_NONE)
        chooseOption(sequentialButton, ManageServerIoTestPage.SEQUENTIAL_NO)
        threadCountInput = "5"
        timeToRunInput = "3"
        chunkSizeInput = "4"

        testDiskPerformanceButton.click()
        waitFor { testResultsHeader }
    }
}
