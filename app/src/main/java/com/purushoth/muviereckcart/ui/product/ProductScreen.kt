package com.purushoth.muviereckcart.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.purushoth.muviereckcart.R
import com.purushoth.muviereckcart.data.ProductsItem
import com.purushoth.muviereckcart.data.Route
import com.purushoth.muviereckcart.data.piShadow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, productViewModel: ProductViewModel = hiltViewModel()) {
    val productData = productViewModel.productPager.collectAsLazyPagingItems()

    val lstCartList = productViewModel.lstCarts.collectAsState()
    Scaffold(topBar = {
        TopAppBar (title = {
            Text(text = stringResource(R.string.title_product))
        }, actions = {
            BadgedBox(modifier = Modifier.padding(end = Dimens.double_space).clickable {
                                                                                       navController.navigate(Route.CART)
            }, badge = {
                Badge {
                    Text(text = lstCartList.value.size.toString())
                }
            }) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Short Cart")
            }

        })
    }) {
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(it), ) {
            items(productData) { value: ProductsItem? ->
                Surface (modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.space)
                    .piShadow()) {
                   Row (modifier = Modifier.padding(Dimens.double_space), verticalAlignment = Alignment.CenterVertically) {
                       AsyncImage(modifier = Modifier
                           .size(
                               100.dp
                           )
                           .aspectRatio(1f), model = value?.thumbnail, contentDescription =  value?.title)
                       Column (modifier = Modifier.padding(start = Dimens.double_space, end = Dimens.space)) {
                           Text(text = value?.title?:"", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black )
                           Spacer(modifier = Modifier.height(Dimens.space))
                           Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                               Text(text = ("₹ ${value?.price.toString()}") ?: "", color = MaterialTheme.colorScheme.outline)
                               Button(onClick = { productViewModel.addToCart(value) }) {
                                   Text(text = "Add Cart")
                               }
                           }

                       }

                   }

                }
            }
        }
    }

}