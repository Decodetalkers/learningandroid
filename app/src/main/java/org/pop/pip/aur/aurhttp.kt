package org.pop.pip.aur

import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    object Begin : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()
}

const val AUR_URL = "https://aur.archlinux.org/rpc/"

enum class RequestType {
    Package,
    User,
    MakeDepends
}

fun requestPackage(packageName: String, requestType: RequestType = RequestType.Package): Request {
    var formPre = FormBody.Builder().add("v", "5").add("type", "search")
    when (requestType) {
        RequestType.User -> formPre = formPre.add("by", "maintainer")
        RequestType.MakeDepends -> formPre = formPre.add("by", "makedepends")
        else -> {}
    }
    val formbody: RequestBody = formPre.add("arg", packageName).build()
    return Request.Builder().url(AUR_URL).post(formbody).build()
}
