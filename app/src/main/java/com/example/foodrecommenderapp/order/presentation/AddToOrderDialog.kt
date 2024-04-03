package com.example.foodrecommenderapp.order.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme

@Composable
fun AddToOrderDialog(
    onDismissDialog: () -> Unit,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
    title: String,
    cancelTitle: String,
    confirmTitle: String,
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.secondary,
        onDismissRequest = { onDismissDialog() },
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        icon = {
               Icon(
                   imageVector = Icons.Default.ShoppingCart,
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.onPrimary
               )
        },
        confirmButton = {
            TextButton(onClick = { onClickConfirm() }) {
                Box(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = MaterialTheme.shapes.small

                    ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = confirmTitle.uppercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onClickCancel() }) {
                Box(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = Color.Red.copy(alpha = 0.4f),
                        shape = MaterialTheme.shapes.small

                    ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = cancelTitle.uppercase(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }

            }
        },
    )

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewOrderDialog() {
    FoodRecommenderAppTheme {
        AddToOrderDialog(
            onDismissDialog = {},
            onClickCancel = {},
            onClickConfirm = {},
            title = "Add to Order",
            cancelTitle = "Cancel",
            confirmTitle = "Confirm"
        )
    }
}