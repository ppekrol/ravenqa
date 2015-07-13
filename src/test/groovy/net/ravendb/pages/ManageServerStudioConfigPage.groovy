package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerStudioConfigPage extends Page {

    static at = {
        header.text() == "\"        Studio config                    \""
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        header { $("div.panel-heading") }
    }
}
