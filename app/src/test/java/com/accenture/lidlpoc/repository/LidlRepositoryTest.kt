package com.accenture.lidlpoc.repository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.accenture.lidlpoc.utils.TestCoroutineRule
import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.DataModel
import com.accenture.lidlpoc.domain.repository.LidlRepository
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LidlRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: LidlApi

    private var lidlRepository : LidlRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        DataModel.couponList
        lidlRepository = LidlRepository(api)

    }

    @Test
    fun givenFlowData_whenGetCouponsMethod_shouldReturnCouponList() {
        val list = DataModel.couponList
        val response_data = Response.success(list)

        testCoroutineRule.runBlockingTest {
            doReturn(response_data)
                .`when`(api)
                .getCoupons()
            val result = lidlRepository?.getCoupons()
            result?.collect {
                    result ->
                when (result) {
                    is LidlApiResult.Success -> {
                        assertTrue(result.success?.isNotEmpty()!!)
                    }
                }
            }

        }
    }

    @Test
    fun givenFlowData_whenGetCouponMethod_shouldReturnCoupon() {
        val list = DataModel.getCouponDetail(1)
        val response_data = Response.success(list)

        testCoroutineRule.runBlockingTest {
            doReturn(response_data)
                .`when`(api)
                .getCouponDetail(1)
            val result = lidlRepository?.getCouponDetail(1)
            result?.collect {result ->
                when (result) {
                    is LidlApiResult.Success -> {
                        assertNotNull(result.success)
                    }
                }

            }
        }

    }

    @Test
    fun givenFlowData_whenActivateMethod_shouldReturnBoolean() {
        val response_data = Response.success(true)

        testCoroutineRule.runBlockingTest {
            doReturn(response_data)
                .`when`(api)
                .activateCoupon(1)
            val result = lidlRepository?.activateCoupon(1)
            result?.collect {result ->
                when (result) {
                    is LidlApiResult.Success -> {
                        assertNotNull(result.success)
                    }
                }

            }
        }
    }

    @Test
    fun givenFlowData_whenCancelMethod_shouldReturnBoolean() {
        val response_data = Response.success(true)

        testCoroutineRule.runBlockingTest {
            doReturn(response_data)
                .`when`(api)
                .cancelCoupon(1)
            val result = lidlRepository?.cancelCoupon(1)
            result?.collect {result ->
                when (result) {
                    is LidlApiResult.Success -> {
                        assertNotNull(result.success)
                    }
                }

            }
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

}