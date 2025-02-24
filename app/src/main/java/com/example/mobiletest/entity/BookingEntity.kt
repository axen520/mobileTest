package com.example.mobiletest.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("booking_list")
data class BookingEntity(
    @PrimaryKey
    val id: Int? = 0,
    @Embedded
    val originAndDestinationPair: OriginAndDestinationEntity? = null
) {
    data class OriginAndDestinationEntity(
        @Embedded("destination_")
        val destination: DestinationEntity? = null,
        @ColumnInfo("destination_city")
        val destinationCity: String? = "",
        @Embedded("origin_")
        val origin: DestinationEntity? = null,
        @ColumnInfo("origin_city")
        val originCity: String? = null,
    )

    data class DestinationEntity(
        val code: String? = "",
        @ColumnInfo("display_name")
        val displayName: String? = "",
        val url: String? = ""
    )
}