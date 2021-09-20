package com.doitintl.blaster.deleters

import com.doitintl.blaster.deleter.GCEBaseDeleter
import com.doitintl.blaster.shared.Constants.ID
import com.doitintl.blaster.shared.Constants.LOCATION
import com.doitintl.blaster.shared.Constants.PROJECT

class DiskDeleter : GCEBaseDeleter() {

    override val pathPatterns: Array<String>
        get() = arrayOf("//compute.googleapis.com/projects/{PROJECT}/zones/{LOCATION}/disks/{ID}")


    override fun doDelete(p: Map<String, String>) {
        val computeService = getComputeService()
        val request = computeService.disks().delete(p[PROJECT]!!, p[LOCATION]!!, p[ID]!!)
        val operation = request.execute()
        waitOnZoneOperation(p[PROJECT]!!, p[LOCATION]!!, operation)
    }
}