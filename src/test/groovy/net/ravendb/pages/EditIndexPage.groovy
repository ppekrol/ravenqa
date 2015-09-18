package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.TopNavigationBar


class EditIndexPage extends Page {

    final static String TRY_INDEX = "Try index"
    final static String MAKE_PERMANENT = "Make permanent"
    final static String CSHARP_INDEX_DEFINITION_DOCUMENT_CODE = """
        using Raven.Abstractions;
        using System.Linq;
        using System.Collections.Generic;
        using System.Collections;
        using System;
        using System.Globalization;
        using System.Text.RegularExpressions;
        using Raven.Client.Indexes;
        using Raven.Abstractions.Indexing;
        using Raven.Abstractions.Data;

        public class OrdersByCompany : AbstractIndexCreationTask
        {
            public override string IndexName {
                get {
                    return "Orders/ByCompany";
                }
            }
            public override IndexDefinition CreateIndexDefinition()
            {
                return new IndexDefinition {
                    Map = @"from order in docs.Orders
        select new { order.Company, Count = 1, Total = order.Lines.Sum(l=>(l.Quantity * l.PricePerUnit) *  ( 1 - l.Discount)) }",
                    Reduce = @"from result in results
        group result by result.Company into g
        select new
        {
            Company = g.Key,
            Count = g.Sum(x=>x.Count),
            Total = g.Sum(x=>x.Total)
        }"
                };
            }
        }

    """.replaceAll("\\s","")

    static at = {
        indexNameInput
    }

    static content = {
        // modules
        topNavigation { module TopNavigationBar }
        alert { module AlertTextModule }

        //tool bar
        csharpButton { $("button[title='Generate C# index definition']") }
        menuToolbar { $('.btn-toolbar') }
        editOptionButtonSelector { ".dropdown-toggle" }
        editDropdownMenuSelector { "ul.dropdown-menu li" }

        // generated class modal dialog
        csharpIndexDefinitionModalHeader(required:false) { $("h4", text:"C# Index Definition") }
        csharpIndexDefinitionCodeContainer(required:false) { csharpIndexDefinitionModalHeader.parent().parent().$("textarea") }

        warningContainer { $('div.alert-warning') }

        // form
        indexNameInput { $("input#indexName") }
        mapsEditors { $("pre#indexEditor textarea") }
    }

    def clickEditIndexOption(CharSequence optionName) {
        def container = menuToolbar
        container.find(editOptionButtonSelector).click()
        def option
        container.find(editDropdownMenuSelector).each {
            if(it.getAttribute("innerHTML").contains(optionName)) {
                option = it
            }
        }
        assert option
        option.click()
    }
}
