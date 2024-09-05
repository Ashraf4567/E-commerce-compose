package com.example.e_commerce_compose.domain.repository

import com.example.e_commerce_compose.data.model.AddNewAddressRequest
import com.example.e_commerce_compose.data.model.AddressDto
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun addAddress(addressRequest: AddNewAddressRequest): Flow<Resource<List<AddressDto?>?>>
    suspend fun getAddresses(): Flow<Resource<List<AddressDto?>?>>

}