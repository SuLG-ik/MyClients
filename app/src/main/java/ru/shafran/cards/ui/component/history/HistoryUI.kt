package ru.shafran.cards.ui.component.history

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shafran.cards.data.card.CardActionModel
import ru.shafran.cards.ui.component.cardsdetails.CardDetailsUI
import ru.shafran.cards.ui.component.cardsdetails.info.CardHistoryUsageItem
import ru.shafran.cards.ui.component.cardsdetails.info.OutlinedSurface

@Composable
fun HistoryUI(component: History, modifier: Modifier = Modifier) {
    CardDetailsUI(component = component.cardDetails, modifier = modifier) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            val mappedHistory = component.history.collectAsState(initial = null).value
            val refreshState = rememberSwipeRefreshState(mappedHistory == null)
            val history = mappedHistory?.filterIsInstance<CardActionModel.Usage>()
            when {
                history == null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.25f))
                        Text("Загрузка...", style = MaterialTheme.typography.h5)
                    }
                }
                history.isEmpty() -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("История пуста(", style = MaterialTheme.typography.h5)
                    }
                }
                else -> {
                    SwipeRefresh(state = refreshState, onRefresh = component::onUpdate) {
                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)) {
                            itemsIndexed(history) { index, item ->
                                OutlinedSurface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(MaterialTheme.shapes.medium)
                                        .clickable {
                                            component.onChooseUsage(item.activationId)
                                        }
                                        .animateContentSize()
                                ) {
                                    CardHistoryUsageItem(
                                        action = item,
                                        employee = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(15.dp)
                                            .animateContentSize()
                                    )
                                }
                                if (index < history.size) {
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
