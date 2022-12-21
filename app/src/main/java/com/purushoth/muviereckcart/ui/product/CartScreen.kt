package com.purushoth.muviereckcart.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.purushoth.muviereckcart.R
import com.purushoth.muviereckcart.data.piShadow
import com.purushoth.muviereckcart.data.toCurrency
import com.purushoth.muviereckcart.ui.product.common.CartViewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(productViewModel: ProductViewModel = hiltViewModel()) {

    val cartItems by productViewModel.lstCarts.collectAsState()
    Scaffold(topBar = {
        TopAppBar (title = {
            Text(text = stringResource(R.string.title_cart))
        })
    }) {

        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(it)) {
            val (list, summary) = createRefs()

            LazyColumn(modifier = Modifier.fillMaxWidth()
                .padding(Dimens.double_space).constrainAs(list) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(summary.top)
                    height = Dimension.fillToConstraints
                }, verticalArrangement = Arrangement.spacedBy(Dimens.space)) {
                items(cartItems) { item->
                    Surface (modifier = Modifier
                        .padding(start = Dimens.space, end = Dimens.space)
                        .piShadow()) {
                        CartViewItem(cartDataItem = item, viewModel = productViewModel)

                    }
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth().padding(Dimens.double_space)
                .constrainAs(summary) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = stringResource(R.string.amount),
                        fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    Text(text = cartItems.sumOf { prod-> prod.quantity* (prod.price?:0) }.toCurrency(), color = MaterialTheme.colorScheme.outline)

                }
                
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Invoice")
                }
            }
        }

    }
}