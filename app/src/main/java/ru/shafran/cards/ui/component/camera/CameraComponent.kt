package ru.shafran.cards.ui.component.camera

import android.util.Log
import androidx.camera.core.ImageProxy
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.shafran.cards.analysis.CardTokenAnalyser
import ru.shafran.cards.utils.get

class CameraComponent(
    componentContext: ComponentContext,
    private val onDetected: (String) -> Unit,
    override val isDetailShown: StateFlow<Boolean>,
) :
    Camera, ComponentContext by componentContext {

    override val isEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)


    private val viewmodel = instanceKeeper.getOrCreate { ViewModel(get()) }


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
                onDetected(it)
        }
    }

    override fun onDetected(cardToken: String) {
        onDetected.invoke(cardToken)
    }


    class ViewModel(private val analyser: CardTokenAnalyser) : InstanceKeeper.Instance {

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