package org.pop.pip.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.pop.pip.aur.AurInfo
import org.pop.pip.aur.AurResult
import org.pop.pip.aur.Resource
import org.pop.pip.aur.requestPackage

private val client = OkHttpClient()

class HttpViewModel : ViewModel() {
    val state = mutableStateOf<Resource<AurInfo>>(Resource.Begin)
    fun searchPackage(packageName: String) {
        if (packageName.isEmpty()) {
            state.value = Resource.Begin
            return
        }
        val thestate by state
        if (thestate is Resource.Loading) return
        viewModelScope.launch {
            searchPackageInner(packageName).collect { response -> state.value = response }
        }
    }
    private suspend fun searchPackageInner(packageName: String) = flow {
        emit(Resource.Loading)
        val request = requestPackage(packageName)
        client.newCall(request)
                .enqueue(
                        object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                state.value = Resource.Failure("Unexpected code ${e}")
                            }

                            override fun onResponse(call: Call, response: Response) {

                                if (response.isSuccessful) {
                                    val bodys = response.body!!.string()

                                    val objs = Json.decodeFromString<AurInfo>(bodys)
                                    state.value = Resource.Success(objs)
                                }
                            }
                        }
                )
    }
}

class SearchPanelModel : ViewModel() {

    var searchValue = mutableStateOf<String>(String())
    var oldValue = mutableStateOf<String>(String())

    fun onValueChanged(value: String) {
        searchValue.value = value
    }

    fun updateOldValue() {
        oldValue.value = searchValue.value
    }
}

class DetailModel : ViewModel() {
    val detailData = mutableStateOf<AurResult?>(null)

    fun setData(value: AurResult) {
        detailData.value = value
    }
}
