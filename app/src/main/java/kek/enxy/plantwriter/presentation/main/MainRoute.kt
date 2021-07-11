package kek.enxy.plantwriter.presentation.main

import kek.enxy.data.readwrite.model.Dump

interface MainRoute {
    fun openTagDetails(dump: Dump)
    fun onReturn()
}
