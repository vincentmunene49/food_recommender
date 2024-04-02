package com.example.foodrecommenderapp.order.presentation

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodrecommenderapp.admin.menu.model.Menu
import com.example.foodrecommenderapp.common.UiEvent
import com.example.foodrecommenderapp.common.presentation.components.RecommenderAppButton
import com.example.foodrecommenderapp.home.presentation.AllMealsComponent
import com.example.foodrecommenderapp.home.presentation.HomeScreenEvents
import com.example.foodrecommenderapp.home.presentation.HomeSharedViewModel
import com.example.foodrecommenderapp.home.presentation.HomeState
import com.example.foodrecommenderapp.navigation.Route
import com.example.foodrecommenderapp.ui.theme.FoodRecommenderAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber

@Composable
fun OrderScreen(
    viewModel: HomeSharedViewModel
) {
    OrderScreenContent(
        state = viewModel.state,
        uiEvent = viewModel.uiEvent,
        onEvents = viewModel::onEvent
    )
}

@Composable
fun OrderScreenContent(
    state: HomeState,
    uiEvent: Flow<UiEvent>,
    onEvents: (HomeScreenEvents) -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.orders ?: emptyList()) { order ->
                OrderComponent(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    quantity = order.quantity.toString(),
                    meal = order.menu,
                    onClickDelete = {
                        onEvents(HomeScreenEvents.DeleteOrder(order))
                    }
                )

            }

        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Total Price: Ksh.${state.totalPrice}",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}


@Composable
fun OrderComponent(
    modifier: Modifier = Modifier,
    quantity: String,
    meal: Menu,
    onClickDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AllMealsComponent(
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Quantity Ordered",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                quantity,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }


        IconButton(onClick = { onClickDelete() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenContentPreview() {
    FoodRecommenderAppTheme {
        OrderScreenContent(
            state = HomeState(),
            onEvents = { },
            uiEvent = emptyFlow()
        )
    }

}