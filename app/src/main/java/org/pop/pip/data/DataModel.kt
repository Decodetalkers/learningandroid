package org.pop.pip.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.pop.pip.aur.AurInfo
import org.pop.pip.aur.RequestPackage
import org.pop.pip.aur.Resource

class HttpViewmodel : ViewModel() {
    val state = mutableStateOf<Resource<AurInfo>>(Resource.Begin)
    fun searchPackage(packageName: String) {
        viewModelScope.launch {
            searchPackageInner(packageName).collect { response -> state.value = response }
        }
    }
    suspend fun searchPackageInner(packageName: String) = flow {
        emit(Resource.Loading)
        val result = RequestPackage(packageName)
        emit(result)
    }
}
