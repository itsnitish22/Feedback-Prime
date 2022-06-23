package com.example.feedbackprime.callprocess.api

import com.example.feedbackprime.callprocess.api.models.*
import retrofit2.http.*

interface SymblAPI {
    @POST("oauth2/token:generate")
    suspend fun postRequestToGetAccessToken(@Body generateAccessTokenBody: BodyToGenerateAccessToken): AccessTokenRetrieved

    @POST("v1/process/video/url")
    suspend fun postUrlAndVideoToGetConversationId(
        @HeaderMap headers: Map<String, String>,
        @Body getConversationId: BodyToGetConversationId
    ): ConversationIdRetrieved

    @GET("v1/job/{jobId}")
    suspend fun jobStatus(
        @Path("jobId") jobId: String,
        @HeaderMap headers: Map<String, String>
    ): GettingJobStatus

    @GET("v1/conversations/{convId}/messages?sentiment=true")
    suspend fun postConvIdToGetFinalResult(
        @Path("convId") convId: String,
        @HeaderMap headers: Map<String, String>
    ): FinalResultFromAPI
}