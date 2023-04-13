package com.example.testtaskarkanit.domain.usecase

import com.example.testtaskarkanit.data.network.RepositoryDataSource
import com.example.testtaskarkanit.domain.model.user.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetListUsersFlowUseCase @Inject constructor(
    private val repositoryDataSource: RepositoryDataSource,
    @Named("IO")
    override val dispatcher: CoroutineDispatcher
) : BaseFlowUseCase<GetListUsersUseCaseParam, User> {
    override fun execute(param: GetListUsersUseCaseParam): Flow<Result<User>> = flow {
        val responseUsers = repositoryDataSource.getUsers(param.request)
        emit(Result.success(responseUsers))
    }

}

data class GetListUsersUseCaseParam(
    val request: String
)