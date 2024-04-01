package com.example.foodrecommenderapp.order.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

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
        onDismissRequest = { onDismissDialog() },
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            TextButton(onClick = { onClickConfirm() }) {
                Text(
                    confirmTitle.uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onClickCancel() }) {
                Text(cancelTitle.uppercase())
            }
        },
    )

}