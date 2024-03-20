package org.pop.pip.aur

import androidx.room.*
import kotlinx.serialization.*

@Entity(tableName = "history_table")
@Serializable
data class AurResult(
        @PrimaryKey val ID: Int,
        @ColumnInfo(name = "name") val Name: String,
        @ColumnInfo(name = "package_id") val PackageBaseID: Int,
        @ColumnInfo(name = "package_base") val PackageBase: String,
        @ColumnInfo(name = "description") val Description: String? = null,
        @ColumnInfo(name = "url") val URL: String? = null,
        @ColumnInfo(name = "num_votes") val NumVotes: Int,
        @ColumnInfo(name = "popularity") val Popularity: Float,
        @ColumnInfo(name = "out_of_date") val OutOfDate: Long?,
        @ColumnInfo(name = "maintainer") val Maintainer: String?,
        @ColumnInfo(name = "first_submitted") val FirstSubmitted: Long,
        @ColumnInfo(name = "lastmodified") val LastModified: Long,
        @ColumnInfo(name = "url_path") val URLPath: String,
        @ColumnInfo(name = "depends") val Depends: List<String>? = null,
        @ColumnInfo(name = "make_depends") val MakeDepends: List<String>? = null,
        @ColumnInfo(name = "license") val License: List<String>? = null,
        @ColumnInfo(name = "keywords") val Keywords: List<String>? = null,
        @ColumnInfo(name = "version") val Version: String
)

@Serializable
data class AurInfo(
        val version: Int,
        val type: String,
        val resultcount: Int,
        val results: List<AurResult>,
        val error: String? = null,
)
