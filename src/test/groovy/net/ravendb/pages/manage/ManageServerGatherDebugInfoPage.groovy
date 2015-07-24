package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerGatherDebugInfoPage extends Page {

    static at = {
        importButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        importButton { $("button", text:"Import") }
    }
}
