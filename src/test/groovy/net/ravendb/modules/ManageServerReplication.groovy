package net.ravendb.modules

import geb.Module


class ManageServerReplication extends Module {

    final static String CLIENT_FAILOVER_LET_CLIENT_DECIDE = "Let client decide"
    final static String CLIENT_FAILOVER_ALLOW_READS_FROM_SECONDARIES = "Allow reads from secondaries"
    final static String CLIENT_FAILOVER_ALLOW_READS_FROM_SECONDARIES_WHEN_TRESHOLD = "Allow read from secondaries when request time threshold is surpassed"
    final static String CLIENT_FAILOVER_ALLOW_READS_FROM_SECONDARIES_AND_WRITES_TO_SECONDARIES = "Allow reads from secondaries and writes to secondaries"
    final static String CLIENT_FAILOVER_FAILS_IMMEDIATELY = "Fail immediately"
    final static String CLIENT_FAILOVER_READ_FROM_ALL_SERVERS = "Read from all servers"
    final static String CLIENT_FAILOVER_READ_FROM_ALL_SERVERS_AND_ALLOW_WRITE_TO_SECONDARIES = "Read from all servers and allow write to secondaries"

    final static String CONFLICT_RESOLUTION_NONE = "None"
    final static String CONFLICT_RESOLUTION_LOCAL = "local"
    final static String CONFLICT_RESOLUTION_REMOTE = "remote"
    final static String CONFLICT_RESOLUTION_LATEST = "latest"

    static content = {
        createGlobalConfigurationForReplication { $("button", text:"Create global configuration for Replication") }

        clientFailoverBehaviourButton { $("div[data-bind='with: replicationsSetup']").$("button") }
        clientFailoverBehaviourOptions { clientFailoverBehaviourButton.$("span") }\

        conflictResolutionButton { $("div[data-bind='with: replicationConfig']").$("button") }
        conflictResolutionOptions { conflictResolutionButton.$("span") }

        addDestinationButton { $("button[title='Add a replication destination (Alt+N)']") }

    }

}
