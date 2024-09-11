package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.model.address.AddNewAddressRequest
import com.example.e_commerce_compose.data.model.address.AddressDto
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.domain.repository.AddressRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

class AddressRepositoryImpl(
    private val webServices: WebServices
): AddressRepository {
    override suspend fun addAddress(addressRequest: AddNewAddressRequest): Flow<Resource<List<AddressDto?>?>> {
        return safeApiCall { webServices.addAddress(addressRequest) }
    }

    override suspend fun getAddresses(): Flow<Resource<List<AddressDto?>?>> {
        return safeApiCall { webServices.getAddresses() }
    }
}