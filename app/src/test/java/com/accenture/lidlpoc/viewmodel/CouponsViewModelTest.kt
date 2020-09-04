package com.accenture.lidlpoc.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.accenture.lidlpoc.utils.TestCoroutineRule
import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.model.DataModel
import com.accenture.lidlpoc.domain.model.ErrorResponse
import com.accenture.lidlpoc.domain.repository.LidlRepository
import com.accenture.lidlpoc.domain.usecase.ActivateCouponUseCase
import com.accenture.lidlpoc.domain.usecase.CancelCouponUseCase
import com.accenture.lidlpoc.domain.usecase.GetCouponDetailUseCase
import com.accenture.lidlpoc.domain.usecase.GetCouponsUseCase
import com.accenture.lidlpoc.ui.common.LidlViewResult
import com.accenture.lidlpoc.ui.data.CouponDetailResponse
import com.accenture.lidlpoc.ui.data.CouponResponse
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CouponsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: LidlApi

    private var getCouponsUseCase: GetCouponsUseCase? = null
    private var lidlRepository : LidlRepository? = null
    val viewModel = CouponsViewModel()

    @Mock
    private lateinit var couponsObserver: Observer<LidlViewResult<List<CouponResponse>, ErrorResponse>>

    @Mock
    private lateinit var couponsDetail: Observer<LidlViewResult<CouponDetailResponse, ErrorResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        DataModel.couponList
        lidlRepository = LidlRepository(api)
        getCouponsUseCase = GetCouponsUseCase(lidlRepository!!)
        viewModel.getCouponsUseCase = getCouponsUseCase
        viewModel.detailCouponUseCase = GetCouponDetailUseCase(lidlRepository!!)
        viewModel.activateCouponsUseCase = ActivateCouponUseCase(lidlRepository!!)
        viewModel.cancelCouponUseCase = CancelCouponUseCase(lidlRepository!!)

    }

    @Test
    fun givenServerResponse200_whenGetCoupons_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(Response.success(DataModel.couponList))
                .`when`(api)
                .getCoupons()
            viewModel.getCoupons()
            viewModel.couponLiveData.observeForever(couponsObserver)
            verify(api).getCoupons()
            viewModel.couponLiveData.removeObserver(couponsObserver)
        }
    }
    @Test
    fun givenServerResponse200_whenGetCouponDetail_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val result_data = DataModel.getCouponDetail(1)
            doReturn(Response.success(result_data))
                .`when`(api)
                .getCouponDetail(anyInt())
            viewModel.getCouponDetail(1)
            viewModel.couponDetailLiveData.observeForever(couponsDetail)
            verify(api).getCouponDetail(anyInt())
            assertNotNull(viewModel.couponDetailLiveData.value)
            viewModel.couponDetailLiveData.removeObserver(couponsDetail)
        }
    }

    @Test
    fun givenServerResponse200_whenActivateCoupon_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(Response.success(true))
                .`when`(api)
                .activateCoupon(anyInt())
            viewModel.activateCoupon(1)
            verify(api).activateCoupon(anyInt())
        }
    }

    @Test
    fun givenServerResponse200_whenCancelCoupon_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(Response.success(true))
                .`when`(api)
                .cancelCoupon(anyInt())
            viewModel.cancelCoupon(1)
            verify(api).cancelCoupon(anyInt())
        }
    }

    @Test
    fun givenServerResponse200_whenActivateCoupon_shouldCallGetCoupons() {
        testCoroutineRule.runBlockingTest {
            doReturn(Response.success(true))
                .`when`(api)
                .activateCoupon(anyInt())
            doReturn(Response.success(DataModel.couponList))
                .`when`(api)
                .getCoupons()
            viewModel.couponLiveData.observeForever(couponsObserver)
            viewModel.activateCoupon(1)
            verify(api).activateCoupon(anyInt())
            verify(api).getCoupons()
            viewModel.couponLiveData.removeObserver(couponsObserver)
        }
    }

    @Test
    fun givenServerResponse200_whenCancelCoupon_shouldCallGetCoupons() {
        testCoroutineRule.runBlockingTest {
            doReturn(Response.success(true))
                .`when`(api)
                .cancelCoupon(anyInt())
            doReturn(Response.success(DataModel.couponList))
                .`when`(api)
                .getCoupons()
            viewModel.couponLiveData.observeForever(couponsObserver)
            viewModel.cancelCoupon(1)
            verify(api).cancelCoupon(anyInt())
            verify(api).getCoupons()
            viewModel.couponLiveData.removeObserver(couponsObserver)
        }
    }


    @After
    fun tearDown() {
        // do something if required
    }

}