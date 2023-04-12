package com.example.testtaskarkanit.domain.usecase

import com.example.testtaskarkanit.data.network.DataSource
import com.example.testtaskarkanit.domain.model.repoContent.RepoContentItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetRepoContentUseCase @Inject constructor(
    private val dataSource: DataSource,
    @Named("IO")
    override val dispatcher: CoroutineDispatcher
) : BaseUseCase<GetRepoContentUseCaseParam, List<RepoContentItem>> {
    override fun execute(param: GetRepoContentUseCaseParam): Flow<Result<List<RepoContentItem>>> = flow {
        val responseContent = dataSource.getRepositoryContent(
            owner = param.owner,
            repo = param.repo,
            path = param.path
        )
        emit(Result.success(responseContent))
    }

}

data class GetRepoContentUseCaseParam(
    val owner: String,
    val repo: String,
    val path: String
)