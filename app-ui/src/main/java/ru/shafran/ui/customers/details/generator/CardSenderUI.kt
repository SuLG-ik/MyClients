package ru.shafran.ui.customers.details.generator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import ru.shafran.common.customers.details.generator.CardSender
import ru.shafran.common.loading.Loading
import ru.shafran.ui.R
import ru.shafran.ui.customers.details.info.CustomerInfo
import ru.shafran.ui.customers.details.info.PlaceholderCustomerInfo
import ru.shafran.ui.view.OutlinedSurface
import ru.shafran.ui.view.QRCode

@Composable
fun CustomerPlaceholderSenderUI(component: Loading, modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Spacer(modifier = Modifier
                    .size(250.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .placeholder(true, highlight = PlaceholderHighlight.fade()))
            }
            PlaceholderCustomerInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
        }
        OutlinedButton(
            onClick = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_share_qr), modifier = Modifier
                .placeholder(true, highlight = PlaceholderHighlight.fade()))
        }
    }
}


@Composable
fun CustomerSenderUI(component: CardSender, modifier: Modifier) {
    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                QRCode(
                    text = component.token,
                    errorCorrection = ErrorCorrectionLevel.M,
                    modifier = Modifier.size(300.dp),
                )
            }
            OutlinedSurface(
                onClick = component::onProfile,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomerInfo(
                    customer = component.customer.data,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
            }
        }
        OutlinedButton(
            onClick = component::onShare,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.customer_share_qr))
        }
    }
}
