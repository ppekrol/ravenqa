package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerAdminLogsPage extends Page {

    static at = {
        configureConnectionButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        configureConnectionButton { $("button", text:"Configure Connection") }
    }
}
