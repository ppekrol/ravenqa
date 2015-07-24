package net.ravendb.pages.manage

import geb.Page
import net.ravendb.modules.manage.ManageServeSQLReplication;
import net.ravendb.modules.manage.ManageServerCustomFunctions;
import net.ravendb.modules.manage.ManageServerMenu;
import net.ravendb.modules.manage.ManageServerPeriodicExport;
import net.ravendb.modules.manage.ManageServerQuotas;
import net.ravendb.modules.manage.ManageServerReplication;
import net.ravendb.modules.manage.ManageServerVersioning;


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
        customFunctions { module ManageServerCustomFunctions }
        versioning { module ManageServerVersioning }

        // tabs
        periodicExportTab { $("a[href='#admin/settings/globalConfig']") }
        replicationTab { $("a[href='#admin/settings/globalConfigReplication']") }
        sqlReplicationTab { $("a[href='#admin/settings/globalConfigSqlReplication']") }
        quotasTab { $("a[href='#admin/settings/globalConfigQuotas']") }
        customFunctionsTab { $("a[href='#admin/settings/globalConfigCustomFunctions']") }
        versioningTab { $("a[href='#admin/settings/globalConfigVersioning']") }
    }
}
