package net.ravendb.modules

import geb.Module
import net.ravendb.utils.HtmlUtils


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

    final static String STATUS_ENABLED = "Enabled"
    final static String STATUS_DISABLED = "Disabled"

    final static String CREDENTIALS_NONE = "None"
    final static String CREDENTIALS_USER = "User"
    final static String CREDENTIALS_API = "API key"

    final static String FAILOVER_INCLUDE = "Include"
    final static String FAILOVER_SKIP = "Skip"

    final static TRANSITIVE_REPLICATION_CHANGED_ONLY = "Changed only"
    final static TRANSITIVE_REPLICATION_CHANGED_AND_REPLICATED = "Changed and replicated"

    final static String SUCCESS_MESSAGE = "Saved Replication settings."

    static content = {
        yesNoDialog { module YesNoModalDialog }
        messagesContainer { module AlertTextModule }

        container { $("form[data-view='views/manage/globalConfig/globalConfigReplications']") }

        saveButton { $("button[title='Save changes (Alt+S)']") }
        createGlobalConfigurationForReplication { $("button", text:"Create global configuration for Replication") }
        removeGlobalConfigurationForReplication { $("button", text:"Remove global configuration for Replication") }

        clientFailoverBehaviourButton { container.$("div[data-bind='with: replicationsSetup']").$("button")[0] }
        clientFailoverBehaviourOptions { container.$("ul")[0].$("a") }

        conflictResolutionButton { container.$("div[data-bind='with: replicationConfig']").$("button") }
        conflictResolutionOptions { container.$("ul")[1].$("a") }

        addDestinationButton { $("button[title='Add a replication destination (Alt+N)']") }
        destinationContainers { $("div.replication-destination-card") }
        destinationContainerCloseButtonSelector { "button.close" }
        destinationContainerEnabledButtonIndex { 1 }
        destinationContainerEnabledOptionsIndex { 0 }
        destinationContainerUrlInputSelector { "input[type='url']" }
        destinationContainerCredentialsButtonIndex { 2 }
        destinationContainerCredentialsOptionsIndex { 1 }
        destinationContainerAdvancedLinkSelector { "a.advanced-replication-settings" }
        destinationContainerClientVisibleUrlSelector { "input[data-bind=\"value: clientVisibleUrl, valueUpdate: 'afterkeydown'\"]" }
        destinationContainerSkipIndexReplicationCheckboxSelector { "input[role='checkbox']" }
        destinationContainerFailoverButtonIndex { 3 }
        destinationContainerFailoverButtonOptionsIndex { 2 }
        destinationContainerTransitiveReplicationButtonIndex { 4 }
        destinationContainerTransitiveReplicationOptionsIndex { 3 }
        destinationContainerUsernameInput { $("input[data-bind=\"value: username, valueUpdate: 'afterkeydown'\"]") }
        destinationContainerPasswordInput { $("input[data-bind=\"value: password, valueUpdate: 'afterkeydown'\"]") }
        destinationContainerDomainInput { $("input[data-bind=\"value: domain, valueUpdate: 'afterkeydown'\"]") }
        destinationContainserAPIKeyInput { $("input[data-bind=\"value: apiKey, valueUpdate: 'afterkeydown'\"]") }
    }

    def fillBaseForm(String clientBehaviour, CharSequence conflictResolution) {
        clientFailoverBehaviourButton.click()
        def toClick
        clientFailoverBehaviourOptions.each {
            if(it.text() == clientBehaviour) {
                toClick = it
            }
        }
        assert toClick
        toClick.click()

        conflictResolutionButton.click()
        toClick = null
        conflictResolutionOptions.each {
            if(it.text().contains(conflictResolution)) {
                toClick = it
            }
        }
        assert toClick
        toClick.click()
    }

    def addDestination(
        boolean enabled,
        String url,
        String credentials,
        String username = null,
        String password = null,
        String domain = null,
        String APIKey = null,
        boolean advanced = false,
        String clientUrl = null,
        boolean skipIndexReplication = false,
        String failover = null,
        String transitiveReplication = null
        ) {
        addDestinationButton.click()

        def container = destinationContainers[0]
        assert container

        // set status
        container.$("button")[destinationContainerEnabledButtonIndex].click()
        def toClick
        def text
        enabled ? text = ManageServerReplication.STATUS_ENABLED : ManageServerReplication.STATUS_DISABLED
        container.$("ul")[destinationContainerEnabledOptionsIndex].$("a").each {
            if(it.text() == text) {
                toClick = it
            }
        }
        assert toClick
        toClick.click()

        // set url
        container.$(destinationContainerUrlInputSelector) << url

        // set credentials
        container.$("button")[destinationContainerCredentialsButtonIndex].click()
        toClick = null
        container.$("ul")[destinationContainerCredentialsOptionsIndex].$("a").each {
            if(it.text() == credentials) {
                toClick = it
            }
        }
        assert toClick
        toClick.click()

        switch(credentials) {
            case ManageServerReplication.CREDENTIALS_USER:
                destinationContainerUsernameInput = username
                destinationContainerPasswordInput = password
                destinationContainerDomainInput = domain
                break
            case ManageServerReplication.CREDENTIALS_API:
                destinationContainserAPIKeyInput = APIKey
                break
        }

        if(advanced) {
            container.$(destinationContainerAdvancedLinkSelector).click()
            waitFor { container.$(destinationContainerClientVisibleUrlSelector).displayed }

            /// scroll down a bit
            HtmlUtils.scrollIntoView(browser, container.$("button")[destinationContainerTransitiveReplicationButtonIndex])

            container.$(destinationContainerClientVisibleUrlSelector) << clientUrl
            skipIndexReplication ? container.$(destinationContainerSkipIndexReplicationCheckboxSelector).click() : null

            container.$("button")[destinationContainerFailoverButtonIndex].click()
            toClick = null
            container.$("ul")[destinationContainerFailoverButtonOptionsIndex].$("a").each {
                if(it.text() == failover) {
                    toClick = it
                }
            }
            assert toClick
            toClick.click()

            container.$("button")[destinationContainerTransitiveReplicationButtonIndex].click()
            toClick = null
            container.$("ul")[destinationContainerTransitiveReplicationOptionsIndex].$("a").each {
                if(it.text() == transitiveReplication) {
                    toClick = it
                }
            }
            assert toClick
            toClick.click()
        }
    }

    def save() {
        HtmlUtils.scrollToTop(browser)
        saveButton.click()
        waitFor(10, 0.1) {
            messagesContainer.containsMessage(ManageServerReplication.SUCCESS_MESSAGE)
            saveButton.@disabled == "true"
            removeGlobalConfigurationForReplication.displayed
            }
    }

    def remove() {
        HtmlUtils.scrollToTop(browser)
        removeGlobalConfigurationForReplication.click()
        waitFor { yesNoDialog.yesButton.displayed }

        yesNoDialog.yesButton.click()
        waitFor(10, 0.1) {
            createGlobalConfigurationForReplication.displayed
        }
    }

}
