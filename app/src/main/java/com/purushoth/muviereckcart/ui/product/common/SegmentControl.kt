package com.purushoth.muviereckcart.ui.product.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.purushoth.muviereckcart.ui.product.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentControl(
    modifier: Modifier = Modifier,
    count: Int = 1,
    onClick: ((count: Int) -> Unit)? = null,

    ) {
    var remCount by remember { mutableStateOf(count) }

    Card(modifier = modifier.shadow(elevation = Dimens.space, shape = RoundedCornerShape(Dimens.space), clip = true)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (remCount > 1) {
                    remCount--
                    onClick?.invoke(remCount)
                }
            }, enabled = remCount > 1) {
                Text(
                    text = stringResource(com.purushoth.muviereckcart.R.string.minus),
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = remCount.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = Dimens.space, end = Dimens.space)
            )
            IconButton(onClick = {
                remCount++
                onClick?.invoke(remCount)
            }) {
                Text(
                    text = stringResource(com.purushoth.muviereckcart.R.string.plus),
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}