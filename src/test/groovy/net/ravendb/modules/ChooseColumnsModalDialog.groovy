package net.ravendb.modules

import geb.Module


class ChooseColumnsModalDialog extends Module {

    static content = {
        header { $("h4", text:"Choose columns") }

        container { $("form#selectColumnsForm") }
        columnsButton { container.$("button[data-toggle='dropdown']") }
        columnsAuto { container.$("a[data-bind='click: customScheme.bind(\$data, false)']") }
        columnsCustom { container.$("a[data-bind='click: customScheme.bind(\$data, true)']") }

        columnsRow { container.$("table tr")}
        columnsDeleteButtonSelector { "button[data-bind='click: \$root.deleteRow.bind(\$root, \$data)'" }
        columnsBindingInputSelector { "input[autocomplete='off']" }
        columnsTitleInputSelector { 'input[data-bind="value: header, valueUpdate: \'afterkeydown\'"]' }

        newColumnButton { $("button[data-bind='click: insertNewRow, visible: customColumns.customMode()']") }
        okButton { container.$("button", text:"Ok") }
    }

    def deleteColumn(String title) {
        def elementToClick

        columnsRow.each {
            if(it.$(columnsTitleInputSelector).value().equals(title)) {
                elementToClick = it.$(columnsDeleteButtonSelector)
            }
        }

        if(elementToClick) {
            elementToClick.click()
        }
    }

    def addColumn(String binding, String title) {
        int columnsCount = columnsRow.size()
        newColumnButton.click()
        waitFor { columnsRow.size() == columnsCount+1 }

        columnsRow.last().$(columnsBindingInputSelector) << binding
        columnsRow.last().$(columnsTitleInputSelector) << title
    }
}
