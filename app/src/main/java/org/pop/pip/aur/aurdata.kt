package org.pop.pip.aur

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
public data class AurResult(
        val ID: Int,
        val Name: String,
        val PackageBaseID: Int,
        val PackageBase: String,
        val Description: String? = null,
        val URL: String? = null,
        val NumVotes: Int,
        val Popularity: Float,
        val OutOfDate: Int?,
        val Maintainer: String?,
        val FirstSubmitted: Int,
        val LastModified: Int,
        val URLPath: String,
        val Depends: List<String>? = null,
        val MakeDepends: List<String>? = null,
        val License: List<String>? = null,
        val Keywords: List<String>? = null,
        val Version: String
)

@Serializable
public data class AurInfo(
        val version: Int,
        val type: String,
        val resultcount: Int,
        val results: List<AurResult>,
        val error: String? = null,
)
