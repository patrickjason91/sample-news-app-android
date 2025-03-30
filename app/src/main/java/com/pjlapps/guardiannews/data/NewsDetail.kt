package com.pjlapps.guardiannews.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "news_details")
data class NewsDetail(
    @PrimaryKey
    val id: String,
    val type: String,
    val sectionId: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val headline: String,
    val trailText: String,
    val byline: String,
    val thumbnail: String,
    val body: String,
    val createdAt: String,
    val updatedAt: String,
)
