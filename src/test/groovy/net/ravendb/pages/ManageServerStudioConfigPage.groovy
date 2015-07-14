package net.ravendb.pages

import net.ravendb.modules.ManageServerMenu;
import geb.Page


class ManageServerStudioConfigPage extends Page {

    static at = {
        $("div[data-view='views/manage/studioConfig']")
    }

    static content = {
        menu { module ManageServerMenu }
    }
}
