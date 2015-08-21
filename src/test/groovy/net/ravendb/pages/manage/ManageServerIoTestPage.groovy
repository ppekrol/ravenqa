package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerIoTestPage extends Page {

    public final static String TEST_TYPE_SIMPLE = "Simple"
    public final static String TEST_TYPE_BATCH = "Batch"
    public final static String OPERATION_TYPE_READ = "Read"
    public final static String OPERATION_TYPE_WRITE = "Write"
    public final static String OPERATION_TYPE_READ_AND_WRITE = "Read and write"
    public final static String BUFFERING_READ = "Read"
    public final static String BUFFERING_NONE = "None"
    public final static String BUFFERING_READ_AND_WRITE = "Read and write"
    public final static String SEQUENTIAL_YES = "Yes"
    public final static String SEQUENTIAL_NO = "No"

    static at = {
        testDiskPerformanceButton.displayed
        menu.toSystemDatabaseLink.displayed
    }

    static content = {
        menu { module ManageServerMenu }

        testDiskPerformanceButton { $("button", text:"Test Disk Performance") }

        dirLocationInput { $("input#path") }
        fileSizeInput { $("input#fileSize") }
        testTypeButton { $("button#testType") }
        operationTypeButton { $("button#operationType") }
        bufferingButton { $("button#bufferingType") }
        sequentialButton { $("button#sequential") }
        threadCountInput { $("input#threadCount") }
        timeToRunInput { $("input#timeToRunInSeconds") }
        chunkSizeInput { $("input#chunkSize") }

        testResultsHeader(required:false) { $("div", text:"Disk Performance Test Results") }
    }

    def chooseOption(def select, def option) {
        select.click()

        def itemToClick
        select.parent().$("li").each {
            if(it.text().equals(option)) {
                itemToClick = it
            }
        }

        if(itemToClick) {
            itemToClick.click()
        }
    }
}
