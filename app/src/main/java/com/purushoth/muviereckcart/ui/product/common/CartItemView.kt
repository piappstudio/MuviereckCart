package com.purushoth.muviereckcart.ui.product.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.purushoth.muviereckcart.R
import com.purushoth.muviereckcart.data.ProductsItem
import com.purushoth.muviereckcart.data.toCurrency
import com.purushoth.muviereckcart.ui.product.Dimens
import com.purushoth.muviereckcart.ui.product.ProductViewModel


@Composable
fun CartViewItem(cartDataItem: ProductsItem, viewModel: ProductViewModel?) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(Dimens.double_space)
    ) {
        val (profileImage, title, description, delete, segment) = createRefs()
        AsyncImage(
            model = cartDataItem.thumbnail,
            contentDescription = cartDataItem.description,
            modifier = Modifier
                .size(
                    100.dp
                )
                .aspectRatio(1f)
                .constrainAs(profileImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

        if (viewModel != null) {

            var showDeleteOption by remember {
                mutableStateOf(false)
            }

            if (showDeleteOption) {
                AlertDialog(onDismissRequest = {
                    showDeleteOption = false }, title = {
                    Text(text = stringResource(R.string.title_remove_cart_item))
                }, text = {
                    Text(text = stringResource(R.string.msg_remove_item))
                }, confirmButton = {
                    Button(onClick = { showDeleteOption = false
                    viewModel.removeItem(cartDataItem)}) {
                        Text(text = "Yes")
                    }
                }, dismissButton = {
                    Button(onClick = { showDeleteOption = false}) {
                        Text(text = "No")
                    }
                })
            }

            Icon(
                modifier = Modifier
                    .constrainAs(delete) {
                        end.linkTo(parent.end)
                        bottom.linkTo(title.bottom)
                    }
                    .clickable { showDeleteOption = true },
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.acc_delerte_item)
            )
        }

        SegmentControl(
            count = cartDataItem.quantity,
            modifier = Modifier.constrainAs(segment) {
                top.linkTo(delete.bottom, Dimens.double_space)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, onClick = {
                viewModel?.updateCount(quantity = it, item = cartDataItem)
            })


        Text(
            text = cartDataItem.title ?: "",
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(profileImage.end, Dimens.double_space)
                end.linkTo(delete.start)
                width = Dimension.fillToConstraints
                top.linkTo(profileImage.top)
            }
        )

        Text(
            text =  cartDataItem.price?.toCurrency() ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(title.bottom, Dimens.space)
                start.linkTo(title.start)

            }
        )
    }
}