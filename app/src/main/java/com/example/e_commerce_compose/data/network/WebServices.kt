package com.example.e_commerce_compose.data.network

import com.example.e_commerce_compose.data.model.address.AddNewAddressRequest
import com.example.e_commerce_compose.data.model.address.AddressDto
import com.example.e_commerce_compose.data.model.BaseResponse
import com.example.e_commerce_compose.data.model.order.CreateCashOrderResponse
import com.example.e_commerce_compose.data.model.address.ShippingAddressRequest
import com.example.e_commerce_compose.data.model.cart.CartResponse
import com.example.e_commerce_compose.data.model.cart.UpdateQuantityRequest
import com.example.e_commerce_compose.data.model.categories.CategoryDto
import com.example.e_commerce_compose.data.model.categories.SubCategoryDto
import com.example.e_commerce_compose.data.model.order.UserOrdersResponse
import com.example.e_commerce_compose.data.model.products.BrandDto
import com.example.e_commerce_compose.data.model.products.ProductDto
import com.example.e_commerce_compose.domain.model.AddToCartRequest
import com.example.e_commerce_compose.domain.model.CartOperationResponse
import com.example.e_commerce_compose.domain.model.AddToWishlistRequest
import com.example.e_commerce_compose.domain.model.SignInRequest
import com.example.e_commerce_compose.domain.model.SignInResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @GET("api/v1/categories")
    suspend fun getAllCategories(): BaseResponse<List<CategoryDto?>?>

    @GET("api/v1/categories/{id}/subcategories")
    suspend fun getSubCategories(@Path("id")categoryId: String): BaseResponse<List<SubCategoryDto?>?>

    @GET("api/v1/products")
    suspend fun getProductsByFilters(
        @Query("category") categoryId: String? = null,
        @Query("sort") sort: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("brand") brand: String? = null,
        @Query("price[gte]") minPrice: Int? = null,
    ): BaseResponse<List<ProductDto?>?>

    @GET("api/v1/products/{id}")
    suspend fun getProductDetails(@Path("id") productId: String): BaseResponse<ProductDto?>

    @POST("api/v1/auth/signin")
    suspend fun signIn(
        @Body
        signInRequest: SignInRequest
    ): SignInResponse

    @POST("api/v1/wishlist")
    suspend fun addProductToWishList(
        @Body
        addWishListRequest: AddToWishlistRequest
    ): BaseResponse<List<String?>?>

    @GET("api/v1/wishlist")
    suspend fun getWishlist(): BaseResponse<List<ProductDto?>?>

    @DELETE("api/v1/wishlist/{id}")
    suspend fun removeProductFromWishlist(@Path("id") productId: String): BaseResponse<List<String?>?>

    @POST("api/v1/cart")
    suspend fun addProductToCart(
        @Body
        addToCartRequest: AddToCartRequest
    ): BaseResponse<CartOperationResponse>

    @DELETE("api/v1/cart/{id}")
    suspend fun removeProductFromCart(@Path("id") productId: String): CartResponse

    @GET("api/v1/cart")
    suspend fun getCart(): CartResponse

    @PUT("api/v1/cart/{id}")
    suspend fun updateProductCountInCart(
        @Path("id") productId: String,
        @Body updateQuantityRequest: UpdateQuantityRequest
    ): CartResponse

    @POST("api/v1/addresses")
    suspend fun addAddress(
        @Body
        addNewAddressRequest: AddNewAddressRequest
    ): BaseResponse<List<AddressDto?>?>

    @GET("api/v1/addresses")
    suspend fun getAddresses(): BaseResponse<List<AddressDto?>?>

    @GET("/api/v1/brands")
    suspend fun getBrands(): BaseResponse<List<BrandDto?>?>

    @POST("api/v1/orders/{cartId}")
    suspend fun createCashOrder(
        @Body
        shippingAddressRequest: ShippingAddressRequest,
        @Path("cartId") cartId: String
    ): BaseResponse<CreateCashOrderResponse?>

    @GET("api/v1/orders/user/{id}")
    suspend fun getUserOrders(
        @Path("id") userId: String
    ):UserOrdersResponse
}