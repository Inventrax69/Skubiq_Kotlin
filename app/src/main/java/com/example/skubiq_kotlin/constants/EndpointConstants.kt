package com.example.skubiq_kotlin.constants

enum class EndpointConstants {
    None, LoginUserDTO, ProfileDTO, Inbound, PutAwayDTO, Inventory, Exception, CycleCount, Outbound, DenestingDTO, HouseKeepingDTO, ScanDTO, StockTakeDTO, InboundDTO;

    enum class ScanType {
        Unloading, Putaway, Picking, Loading, DeNesting, Assortment
    }
}