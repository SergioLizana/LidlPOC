package com.accenture.lidlpoc.domain.usecase

import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.Coupon
import com.accenture.lidlpoc.domain.model.ErrorResponse
import com.accenture.lidlpoc.domain.repository.LidlRepository
import kotlinx.coroutines.flow.Flow


typealias GetCouponsBaseUseCase = BaseUseCase<Nothing?, Flow<LidlApiResult<List<Coupon>?, ErrorResponse>>>

class GetCouponsUseCase(val lidlRepository: LidlRepository) : GetCouponsBaseUseCase {
    override suspend fun invoke(params: Nothing?): Flow<LidlApiResult<List<Coupon>?, ErrorResponse>> {
        return lidlRepository.getCoupons()
    }

}