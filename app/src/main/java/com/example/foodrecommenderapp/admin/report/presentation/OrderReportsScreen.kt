package com.example.foodrecommenderapp.admin.report.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodrecommenderapp.admin.common.presentation.AdminEvents
import com.example.foodrecommenderapp.admin.common.presentation.AdminSharedViewModel
import com.example.foodrecommenderapp.admin.common.presentation.AdminState
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppButton
import com.example.foodrecommenderapp.home.presentation.AllMealsComponent
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme

@Composable
fun OrderReportsScreen(
    viewModel: AdminSharedViewModel
) {

    OrderReportsScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun OrderReportsScreenContent(
    state: AdminState,
    onEvent: (AdminEvents) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.orders) { order ->
            OrderReportComponent(
                modifier = Modifier.padding(8.dp),
                quantity = order.quantity.toString(),
                meal = order.menu,
                email = order.userEmail,
                price = order.menu.price.toString(),
                onClickDelete = { onEvent(AdminEvents.OnDeleteOrder(order)) }
            )
        }
    }

}


@Composable
private fun OrderReportComponent(
    modifier: Modifier = Modifier,
    quantity: String,
    meal: Menu,
    email: String,
    price: String,
    onClickDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AllMealsComponent(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            onClickFood = { /*TODO*/ },
            imagePath = meal.image ?: "",
            foodTitle = meal.name,
            foodCategory = meal.category,
            price = meal.price.toString()
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Quantity Ordered",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    quantity,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total Price",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Ksh.$price",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Contact Email",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            RecommenderAppButton(onClick = { onClickDelete() }, text = "Clear Order")

        }


    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOrderReportsScreen() {
    FoodRecommenderAppTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            OrderReportsScreenContent(
                state = AdminState(),
                onEvent = { }
            )
        }
    }
}