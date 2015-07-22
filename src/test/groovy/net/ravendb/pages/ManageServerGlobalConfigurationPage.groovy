package net.ravendb.pages

import geb.Page
import net.ravendb.modules.ManageServeSQLReplication
import net.ravendb.modules.ManageServerMenu
import net.ravendb.modules.ManageServerPeriodicExport
import net.ravendb.modules.ManageServerQuotas
import net.ravendb.modules.ManageServerReplication


class ManageServerGlobalConfigurationPage extends Page {

    static at = {
        periodicExportTab
        menu
    }

    static content = {
        //modules
        menu { module ManageServerMenu }
        periodicExport { module ManageServerPeriodicExport }
        replication { module ManageServerReplication }
        sqlReplication { module ManageServeSQLReplication }
        quotas { module ManageServerQuotas }

        // tabs
        periodicExportTab { $("a[href='#admin/settings/globalConfig']") }
        replicationTab { $("a[href='#admin/settings/globalConfigReplication']") }
        sqlReplicationTab { $("a[href='#admin/settings/globalConfigSqlReplication']") }
        quotasTab { $("a[href='#admin/settings/globalConfigQuotas']") }
        customFunctionsTab { $("a[href='#admin/settings/globalConfigCustomFunctions']") }
        versionsTab { $("a[href='#admin/settings/globalConfigVersioning']") }
    }
}
