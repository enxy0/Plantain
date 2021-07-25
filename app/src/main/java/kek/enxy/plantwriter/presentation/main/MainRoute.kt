package kek.enxy.plantwriter.presentation.main

import kek.enxy.data.readwrite.model.Dump

interface MainRoute {
    fun openDumpDetails(dump: Dump)
    fun onReturn()
    fun openDumps()
}
