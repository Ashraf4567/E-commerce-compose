package com.example.e_commerce_compose.data.repositoryImpl

import com.example.e_commerce_compose.data.model.address.AddNewAddressRequest
import com.example.e_commerce_compose.data.model.address.AddressDto
import com.example.e_commerce_compose.data.network.WebServices
import com.example.e_commerce_compose.data.safeApiCall
import com.example.e_commerce_compose.data.toDomain
import com.example.e_commerce_compose.domain.model.Address
import com.example.e_commerce_compose.domain.repository.AddressRepository
import com.example.e_commerce_compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class AddressRepositoryImpl(
    private val webServices: WebServices
): AddressRepository {
    override suspend fun addAddress(addressRequest: AddNewAddressRequest): Flow<Resource<List<AddressDto?>?>> {
        return safeApiCall { webServices.addAddress(addressRequest) }
    }

    override suspend fun getAddresses(): Flow<Resource<List<AddressDto?>?>> {
        return safeApiCall { webServices.getAddresses() }
    }

    override suspend fun deleteAddress(addressId: String): Flow<Resource<List<Address>?>> {
        return safeApiCall { webServices.deleteAddress(addressId) }.map {res->
            when(res){
                is Resource.Success->{
                    Resource.Success(
                        res.data?.map { dto->
                            dto.toDomain()
                        }
                    )
                }
                is Resource.Error -> res
                is Resource.Loading -> res
                is Resource.ServerError -> res
            }

        }
    }
}