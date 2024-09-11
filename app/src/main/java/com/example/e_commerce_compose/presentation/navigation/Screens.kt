package com.example.e_commerce_compose.presentation.navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object Categories: Screens("categories")
    data object WishList: Screens("wishList")
    data object Profile: Screens("profile")
    data object ProductDetails: Screens("productDetails/{productId}"){
        fun createRoute(productId: String) = "productDetails/$productId"
    }
    data object SignIn: Screens("signIn")
    data object Cart: Screens("cart")
    data object Checkout: Screens("checkout/{cartId}"){
        fun createRoute(cartId: String) = "checkout/$cartId"
    }
    data object BrowseProducts: Screens("browseProducts/{categoryId}"){
        fun createRoute(categoryId: String) = "browseProducts/$categoryId"
    }
    data object SuccessPlaceOrder: Screens("successPlaceOrder/{totalPrice}/{paymentMethod}"){
        fun createRoute(totalPrice: Double, paymentMethod: String) = "successPlaceOrder/$totalPrice/$paymentMethod"
    }

    data object MyOrders: Screens("myOrders")
    data object ShoppingAddress: Screens("shoppingAddress")
    data object PaymentMethods: Screens("paymentMethods")
    data object EditProfile: Screens("editProfile")

}