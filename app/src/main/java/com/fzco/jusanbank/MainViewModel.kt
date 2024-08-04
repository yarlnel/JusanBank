package com.fzco.jusanbank

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel : ViewModel(), ContainerHost<MainState, MainSideEffect> {
    override val container: Container<MainState, MainSideEffect> = container(
        initialState = MainState(percentage = 23),
    )

    fun updatePercentage(percentage: Int) = intent {
        reduce {
            state.copy(percentage = percentage)
        }
    }

}