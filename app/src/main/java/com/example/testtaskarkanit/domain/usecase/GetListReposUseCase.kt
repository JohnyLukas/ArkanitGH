package com.example.testtaskarkanit.domain.usecase

import com.example.testtaskarkanit.data.network.DataSource
import com.example.testtaskarkanit.domain.model.repo.Repo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetListReposUseCase @Inject constructor(
    private val dataSource: DataSource,
    @Named("IO")
    override val dispatcher: CoroutineDispatcher
) : BaseUseCase<GetListReposUseCaseParam, Repo> {
    override fun execute(param: GetListReposUseCaseParam): Flow<Result<Repo>> = flow {
        val responseRepos = dataSource.getRepos(param.request)
        emit(Result.success(responseRepos))
    }

}

data class GetListReposUseCaseParam(
    val request: String
)