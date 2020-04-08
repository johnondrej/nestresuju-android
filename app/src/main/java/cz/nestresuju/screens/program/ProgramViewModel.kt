package cz.nestresuju.screens.program

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.repositories.ProgramFirstRepository
import cz.nestresuju.screens.base.BaseViewModel

class ProgramViewModel(
    private val programFirstRepository: ProgramFirstRepository
) : BaseViewModel() {

    val programFirstStream = liveData<ProgramFirstResults> {
        emitSource(programFirstRepository.observeProgramResults().asLiveData())
    }
}