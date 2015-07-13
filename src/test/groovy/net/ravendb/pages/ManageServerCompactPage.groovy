package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerCompactPage extends Page {

    static at = {
        startCompactionButton
        menu
    }

    static content = {
        menu { module ManageServerMenu }

        startCompactionButton { $("button", text:"Start Compaction") }
    }
}
