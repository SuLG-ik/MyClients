package ru.shafran.cards.utils

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinScopeComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

fun ComponentContext.getKoin() =
    KoinPlatformTools.defaultContext().get()

inline fun <reified T : Any> ComponentContext.get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    return if (this is KoinScopeComponent) {
        scope.get(qualifier, parameters)
    } else getKoin().get(qualifier, parameters)
}

inline fun <reified T : Any> ComponentContext.inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> =
    lazy(mode) { get<T>(qualifier, parameters) }


