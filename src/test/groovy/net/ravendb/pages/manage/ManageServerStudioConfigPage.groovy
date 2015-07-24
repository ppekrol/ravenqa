package net.ravendb.pages.manage

import net.ravendb.modules.manage.ManageServerMenu;
import geb.Page


class ManageServerStudioConfigPage extends Page {

    static at = {
        $("div[data-view='views/manage/studioConfig']")
    }

    static content = {
        menu { module ManageServerMenu }
    }
}
