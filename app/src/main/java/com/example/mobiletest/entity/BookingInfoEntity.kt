package com.example.mobiletest.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("booking_info")
data class BookingInfoEntity(
    @PrimaryKey
    @ColumnInfo("ship_reference")
    var shipReference: String = "",
    @ColumnInfo("ship_token")
    var shipToken: String? = "",
    @ColumnInfo("can_issue_ticket_checking")
    var canIssueTicketChecking: Boolean? = false,
    @ColumnInfo("expire_time")
    var expiryTime: String? = "",
    var duration: Long? = 0,
    @Ignore
    @SerializedName("segments")
    private var _segments: List<BookingEntity>? = emptyList()
) {
    val segments: List<BookingEntity> get() {
        return _segments ?: emptyList<BookingEntity>().also { _segments = it }
    }
}
