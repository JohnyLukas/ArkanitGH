package com.example.testtaskarkanit.domain.usecase

import com.example.testtaskarkanit.data.network.RepositoryDataSource
import com.example.testtaskarkanit.domain.model.repo.Repo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetListReposFlowUseCase @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource,
    @Named("IO")
    override val dispatcher: CoroutineDispatcher
) : BaseFlowUseCase<GetListReposUseCaseParam, Repo> {
    override fun execute(param: GetListReposUseCaseParam): Flow<Result<Repo>> = flow {
        val responseRepos = repositoryDataSource.getRepos(param.request)
        emit(Result.success(responseRepos))
    }

}

data class GetListReposUseCaseParam(
    val request: String
)