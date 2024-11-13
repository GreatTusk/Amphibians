package com.f776.amphibians.network

import com.f776.amphibians.model.Amphibian
import retrofit2.http.GET

interface AmphibianApiService {
    @GET("amphibians")
    suspend fun getAmphibianData(): List<Amphibian>
}