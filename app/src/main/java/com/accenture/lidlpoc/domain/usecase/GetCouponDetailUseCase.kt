package com.accenture.lidlpoc.domain.usecase

import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.CouponDetail
import com.accenture.lidlpoc.domain.model.ErrorResponse
import com.accenture.lidlpoc.domain.repository.LidlRepository
import kotlinx.coroutines.flow.Flow


typealias GetCouponDetailBaseUseCase = BaseUseCase<Int, Flow<LidlApiResult<CouponDetail?, ErrorResponse>>>

class GetCouponDetailUseCase(private val lidlRepository: LidlRepository) :
    GetCouponDetailBaseUseCase {
    override suspend fun invoke(params: Int): Flow<LidlApiResult<CouponDetail?, ErrorResponse>> {
        return lidlRepository.getCouponDetail(params)
    }

}