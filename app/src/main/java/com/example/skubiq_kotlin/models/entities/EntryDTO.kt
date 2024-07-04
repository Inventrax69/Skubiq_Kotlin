package com.example.skubiq_kotlin.models.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Prasanna.Ch on 5/15/2019.
 */
class EntryDTO(entries: Set<Map.Entry<*, *>>) {

    @SerializedName("DockNumber")
    var dockNumber: String? = null

    @SerializedName("VehicleNumber")
    var vehicleNumber: String? = null

    @SerializedName("DockID")
    var dockID: String? = null

    init {
        for ((key, value) in entries) {
            when (key.toString()) {
                "DockNumber" -> if (value != null) {
                    this.dockNumber = value.toString()
                }

                "VehicleNumber" -> if (value != null) {
                    this.vehicleNumber = value.toString()
                }

                "DockID" -> if (value != null) {
                    this.dockID = value.toString()
                }
            }
        }
    }
}
