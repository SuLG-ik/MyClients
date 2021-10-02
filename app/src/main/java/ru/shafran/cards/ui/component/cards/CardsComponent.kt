package ru.shafran.cards.ui.component.cards

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.shafran.cards.ui.component.camera.Camera
import ru.shafran.cards.ui.component.camera.CameraComponent
import ru.shafran.cards.ui.component.cardsdetails.CardDetails
import ru.shafran.cards.ui.component.cardsdetails.CardDetailsComponent
import ru.shafran.cards.utils.createCoroutineScope
import ru.shafran.cards.utils.get
import ru.shafran.cards.utils.stores
import ru.shafran.network.card.CardsStore
import ru.shafran.network.employee.EmployeesStore

class CardsComponent(
    componentContext: ComponentContext,
    onOpenSettings: () -> Unit,
) : Cards, ComponentContext by componentContext {

    private val scope = createCoroutineScope()
    private val employeesStore by instanceKeeper.stores { EmployeesStore(get(), get()) }
    private val cardsStore by instanceKeeper.stores { CardsStore(get(), get()) }

    override val details: CardDetails = CardDetailsComponent(
        childContext("details_component"),
        cardsStore = cardsStore,
        employeesStore = employeesStore,
    )
    override val camera: Camera =
        CameraComponent(
            childContext("camera_component"),
            onDetected = this::onShowCardInfo,
            onOpenSettings = onOpenSettings,
        )

    init {
        details.isShown.onEach {
            Log.d("CameraStateCheck" ,"is shown = $it")
            toggleCamera(!it)
        }.launchIn(scope)
    }

    private fun toggleCamera(isEnabled: Boolean) {
        if (isEnabled) camera.onEnable()
        else camera.onPause()
    }

    override fun onShowCardInfo(cardToken: String) {
        details.onShowByCardToken(cardToken)
    }

}