package org.pop.pip.aur

import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

public sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    object Begin : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()
}

private val client = OkHttpClient()

val AUR_URL = "https://aur.archlinux.org/rpc/"

public fun RequestPackage(packageName: String): Resource<AurInfo> {
    val formbody: RequestBody =
            FormBody.Builder()
                    .add("v", "5")
                    .add("type", "search")
                    .add("by", "name")
                    .add("arg", packageName)
                    .build()
    val request = Request.Builder().url("https://aur.archlinux.org/rpc/").post(formbody).build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return Resource.Failure("Unexpected code $response")

        // println(response.body?.string())
        if (response.body == null) return Resource.Failure("No data")
        val bodys = response.body!!.string()

        val objs = Json.decodeFromString<AurInfo>(bodys)
        return Resource.Success(objs)
    }
}
