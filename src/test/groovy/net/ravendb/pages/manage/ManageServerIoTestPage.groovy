package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
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
