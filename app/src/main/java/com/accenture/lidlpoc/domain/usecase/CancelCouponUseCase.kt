package com.accenture.lidlpoc.domain.usecase

import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.ErrorResponse
import com.accenture.lidlpoc.domain.repository.LidlRepository
import kotlinx.coroutines.flow.Flow


typealias CancelCouponBaseUseCase = BaseUseCase<Int, Flow<LidlApiResult<Boolean?, ErrorResponse>>>

class CancelCouponUseCase(private val lidlRepository: LidlRepository) : CancelCouponBaseUseCase {
    override suspend fun invoke(params: Int): Flow<LidlApiResult<Boolean?, ErrorResponse>> {
        return lidlRepository.cancelCoupon(params)
    }

}