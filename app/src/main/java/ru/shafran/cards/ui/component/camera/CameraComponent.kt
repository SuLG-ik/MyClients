package ru.shafran.cards.ui.component.camera

import android.util.Log
import androidx.camera.core.ImageProxy
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.analysis.CardTokenAnalyser
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.CardDetails
import ru.shafran.cards.ui.component.details.CardDetailsComponent
import ru.shafran.cards.utils.inject

class CameraComponent(componentContext: ComponentContext) : Camera, ComponentContext by componentContext {

    override val isEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val details: CardDetails = CardDetailsComponent(childContext("details_component"))

    private val viewmodel = instanceKeeper.getOrCreate { ViewModel() }

    override fun onDisable() {
        isEnabled.value = false
        viewmodel.pause()
    }

    override fun onEnable() {
        isEnabled.value = true
        viewmodel.resume()
    }

    override fun processImage(proxy: ImageProxy) {
        viewmodel.processImage(proxy) {
            Log.d("pisos", it)
            if (it.isNotEmpty())
                details.onShow(DetectedCard(it))
            else
                details.onHide()
        }
    }


    class ViewModel : InstanceKeeper.Instance {

        private val analyser by inject<CardTokenAnalyser>()

        fun processImage(proxy: ImageProxy, onDetected: (String) -> Unit) {
            analyser.process(proxy, onDetected)
        }

        fun pause() {
            analyser.pause()
        }

        fun resume() {
            analyser.resume()
        }

        override fun onDestroy() {
            analyser.pause()
        }

    }

}