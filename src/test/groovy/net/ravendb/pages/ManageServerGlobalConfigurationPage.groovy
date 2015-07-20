package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.ManageServerMenu
import net.ravendb.modules.ManageServerPeriodicExport
import net.ravendb.modules.YesNoModalDialog


class ManageServerGlobalConfigurationPage extends Page {

    static at = {
        periodicExportTab
        menu
    }

    static content = {
        //modules
        menu { module ManageServerMenu }
        periodicExport { module ManageServerPeriodicExport }

        // tabs
        periodicExportTab { $("a[href='#admin/settings/globalConfig']") }
        replicationTab { $("a[href='#admin/settings/globalConfigReplication']") }
        sqlReplicationTab { $("a[href=#admin/settings/globalConfigSqlReplication']") }
        quotasTab { $("a[href='#admin/settings/globalConfigQuotas']") }
        customFunctionsTab { $("a[href='#admin/settings/globalConfigCustomFunctions']") }
        versionsTab { $("a[href='#admin/settings/globalConfigVersioning']") }
    }
}
