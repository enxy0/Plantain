package kek.enxy.plantwriter.presentation.main

import kek.enxy.data.readwrite.model.Dump

interface DumpsContract {
    fun onReturn()
    fun openDumpDetails(dump: Dump)
}
