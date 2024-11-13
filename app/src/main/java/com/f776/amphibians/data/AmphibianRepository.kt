package com.f776.amphibians.data

import com.f776.amphibians.model.Amphibian
import com.f776.amphibians.network.AmphibianApiService

interface AmphibianRepository {
    suspend fun getMarsPhotos(): List<Amphibian>
}

class NetworkAmphibianRepository(private val amphibianApiService: AmphibianApiService) :
    AmphibianRepository {
    override suspend fun getMarsPhotos(): List<Amphibian> {
        return amphibianApiService.getAmphibianData()
    }

}