package org.pop.pip.aur

import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody

public sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    object Begin : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()
}

val AUR_URL = "https://aur.archlinux.org/rpc/"

public fun RequestPackage(packageName: String): Request {
    val formbody: RequestBody =
            FormBody.Builder()
                    .add("v", "5")
                    .add("type", "search")
                    .add("by", "name")
                    .add("arg", packageName)
                    .build()
    return Request.Builder().url("https://aur.archlinux.org/rpc/").post(formbody).build()
}
