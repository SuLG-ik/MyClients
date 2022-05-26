package ru.shafran.common.companies

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import ru.shafran.common.companies.list.CompaniesListComponent
import ru.shafran.common.components.R
import ru.shafran.common.loading.LoadingComponent
import ru.shafran.common.utils.Updatable
import ru.shafran.common.utils.getStore
import ru.shafran.common.utils.replaceAll
import ru.shafran.network.account.data.Account
import ru.shafran.network.companies.CompaniesListStore
import ru.shafran.network.companies.data.Company
import ru.shafran.network.utils.reduceStates

class CompanySelectorComponent(
    componentContext: ComponentContext,
    private val onSelect: (Company) -> Unit,
    override val account: Account,
) : CompanySelector, Updatable, ComponentContext by componentContext {

    private val store = getStore<CompaniesListStore>()
        .reduceStates(this, this::reduceState)

    override val onUpdate: () -> Unit = {
        store.accept(
            CompaniesListStore.Intent.LoadCompaniesList(accountId = account.id)
        )
    }

    private fun reduceState(state: CompaniesListStore.State) {
        when (state) {
            is CompaniesListStore.State.Empty -> onUpdate()
            is CompaniesListStore.State.CompaniesList ->
                router.replaceAll(CompanySelector.Configuration.CompaniesList(state.companies))
            is CompaniesListStore.State.CompaniesListLoading ->
                router.replaceAll(CompanySelector.Configuration.Loading())
        }
    }

    private val router = router<CompanySelector.Configuration, CompanySelector.Child>(
        initialConfiguration = CompanySelector.Configuration.Loading(),
        childFactory = this::createChild,
    )

    private fun createChild(
        configuration: CompanySelector.Configuration,
        componentContext: ComponentContext,
    ): CompanySelector.Child {
        return when (configuration) {
            is CompanySelector.Configuration.CompaniesList ->
                CompanySelector.Child.CompaniesList(
                    CompaniesListComponent(
                        companies = configuration.companies,
                        onSelect = onSelect,
                    )
                )
            is CompanySelector.Configuration.Loading ->
                CompanySelector.Child.Loading(
                    LoadingComponent(R.string.companies_list_loading)
                )
        }
    }

    override val routerState: Value<RouterState<CompanySelector.Configuration, CompanySelector.Child>>
        get() = router.state


}