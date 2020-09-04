package com.accenture.lidlpoc.domain.usecase

import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.ErrorResponse
import com.accenture.lidlpoc.domain.repository.LidlRepository
import kotlinx.coroutines.flow.Flow


typealias ActivateCouponBaseUseCase = BaseUseCase<Int, Flow<LidlApiResult<Boolean?, ErrorResponse>>>

class ActivateCouponUseCase(private val lidlRepository: LidlRepository) :
    ActivateCouponBaseUseCase {
    override suspend fun invoke(params: Int): Flow<LidlApiResult<Boolean?, ErrorResponse>> {
        return lidlRepository.activateCoupon(params)
    }

}