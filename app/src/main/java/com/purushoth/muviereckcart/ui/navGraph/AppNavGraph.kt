package com.purushoth.muviereckcart.ui.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.purushoth.muviereckcart.data.Route
import com.purushoth.muviereckcart.ui.product.CartScreen
import com.purushoth.muviereckcart.ui.product.ProductScreen
import com.purushoth.muviereckcart.ui.product.ProductViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    val productViewModel:ProductViewModel = hiltViewModel()

    NavHost(navController = navController , startDestination = Route.PRODUCT) {

        composable(Route.PRODUCT) {
            ProductScreen(productViewModel)
        }
        composable(Route.CART) {
            CartScreen(productViewModel)
        }

    }
}