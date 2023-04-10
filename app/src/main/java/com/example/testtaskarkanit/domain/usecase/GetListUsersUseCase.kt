package com.example.testtaskarkanit.domain.usecase

import com.example.testtaskarkanit.data.network.DataSource
import com.example.testtaskarkanit.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class GetListUsersUseCase @Inject constructor(
    private val dataSource: DataSource,
    @Named("IO")
    override val dispatcher: CoroutineDispatcher
) : BaseUseCase<GetListUsersUseCaseParam, User> {
    override fun execute(param: GetListUsersUseCaseParam): Flow<Result<User>> = flow {
        val responseUsers = dataSource.getUsers(param.request)
        emit(Result.success(responseUsers))
    }

}

data class GetListUsersUseCaseParam(
    val request: String
)