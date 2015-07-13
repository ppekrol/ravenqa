package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
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
