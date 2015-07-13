package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerIoTestPage extends Page {

    static at = {
        testDiskPerformanceButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        testDiskPerformanceButton { $("button", text:"Test Disk Performance") }
    }
}
