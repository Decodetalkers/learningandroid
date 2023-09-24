package org.pop.pip.aur

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable data class Project(val name: String, val language: String)

public fun runData() {
    val data = Project("kotlinx.serialization", "kotlin")

    val string = Json.encodeToString(data)

    println(string)

    val obj = Json.decodeFromString<Project>(string)

    println(obj)
}

@Serializable
public data class AurResult(
        val ID: Int,
        val Name: String,
        val PackageBaseID: Int,
        val PackageBase: String,
        val Description: String,
        val URL: String,
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
