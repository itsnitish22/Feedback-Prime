package com.example.feedbackprime.callprocess.outputs

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedbackprime.callprocess.api.RetrofitInstance
import com.example.feedbackprime.callprocess.api.models.*
import kotlinx.coroutines.launch

private const val TAGProcessInfo = "Process Info View Model"

class ProcessInfoViewModel : ViewModel() {
    //variables have literal meanings

    var count = 0

    private val _gotAccessToken: MutableLiveData<AccessTokenRetrieved> = MutableLiveData()
    val gotAccessToken: LiveData<AccessTokenRetrieved>
        get() = _gotAccessToken

    private val _gotConversationId: MutableLiveData<ConversationIdRetrieved> = MutableLiveData()
    val gotConversationId: LiveData<ConversationIdRetrieved>
        get() = _gotConversationId

    private val _finalResult: MutableLiveData<FinalResultFromAPI> = MutableLiveData()
    val finalResult: LiveData<FinalResultFromAPI>
        get() = _finalResult

    private val _jobCompleted = MutableLiveData(false)
    val jobCompleted: LiveData<Boolean>
        get() = _jobCompleted

    private val _countOfJobStatus: MutableLiveData<Int> = MutableLiveData()
    val countOfJobStatus: LiveData<Int>
        get() = _countOfJobStatus

    //to get accessToken
    fun getAccessToken() {
        val bodyToGenerateAccessToken = BodyToGenerateAccessToken()
        viewModelScope.launch {
            val fetchedAccessToken =
                RetrofitInstance.api.postRequestToGetAccessToken(
                    bodyToGenerateAccessToken
                )
            _gotAccessToken.value = fetchedAccessToken
        }
    }

    //to get conversationId
    fun getConversationId(accessToken: String, url: String, name: String) {
        val bodyToGetConversationId = BodyToGetConversationId(url, name)
        val headerMap = mutableMapOf<String, String>()

        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.i(TAGProcessInfo, headerMap.toString())

        viewModelScope.launch {
            val fetchedConversationId = RetrofitInstance.api.postUrlAndVideoToGetConversationId(
                headerMap,
                bodyToGetConversationId
            )
            _gotConversationId.value = fetchedConversationId
        }
    }

    //to get jobStatus
    fun getJobStatus(jobId: String, accessToken: String, convId: String) {
        val headerMap = mutableMapOf<String, String>()

        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        viewModelScope.launch {
            val fetchedResult = RetrofitInstance.api.jobStatus(jobId, headerMap)
            if (fetchedResult.status == "completed") {
                _jobCompleted.value = true
            } else {
                _countOfJobStatus.value = ++count
                _jobCompleted.value = false
                Handler(Looper.getMainLooper()).postDelayed({
                    getJobStatus(jobId, accessToken, convId)
                }, 5000)
            }
        }
    }

    //to get finalResult
    fun getFinalResultFromAPI(convId: String, accessToken: String) {
        val headerMap = mutableMapOf<String, String>()

        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        viewModelScope.launch {
            val fetchedResult = RetrofitInstance.api.postConvIdToGetFinalResult(convId, headerMap)
            _finalResult.value = fetchedResult
        }
    }
}