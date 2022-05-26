package ru.shafran.ui.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.shafran.common.auth.AccountAuth
import ru.shafran.ui.R
import ru.shafran.ui.auth.completed.LoadingCompletedUI
import ru.shafran.ui.auth.login.AuthLoginUI
import ru.shafran.ui.auth.type.AuthTypeUI
import ru.shafran.ui.error.ErrorUI
import ru.shafran.ui.loading.LoadingUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthUI(
    component: AccountAuth,
    modifier: Modifier,
) {
    Surface {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                AppLogo(
                    isLoading = component.routerState.subscribeAsState().value.activeChild.configuration is AccountAuth.Configuration.Loading,
                    modifier = Modifier.size(200.dp)
                )
                Children(routerState = component.routerState) {
                    AuthNavHost(child = it.instance, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp))
                }
            }
        }
    }
}

@Composable
private fun AuthNavHost(child: AccountAuth.Child, modifier: Modifier) {
    when (child) {
        is AccountAuth.Child.AuthType ->
            AuthTypeUI(component = child.component, modifier = modifier)
        is AccountAuth.Child.Loading ->
            LoadingUI(component = child.component, modifier = modifier)
        is AccountAuth.Child.LoadingCompleted ->
            LoadingCompletedUI(component = child.component, modifier = modifier)
        is AccountAuth.Child.LoginRequest ->
            AuthLoginUI(component = child.component, modifier = modifier)
        is AccountAuth.Child.Error ->
            ErrorUI(component = child.component, modifier = modifier)
    }
}

@Composable
fun AppLogo(isLoading: Boolean, modifier: Modifier) {
    val animation =
        animateFloatAsState(
            targetValue = 0.75f,
            animationSpec = if (isLoading) infiniteRepeatable(
                tween(150),
                repeatMode = RepeatMode.Reverse,
            ) else tween(50)
        )
    Image(
        painterResource(id = R.drawable.app_icon),
        contentDescription = null,
        modifier = modifier.scale(animation.value),
    )
}