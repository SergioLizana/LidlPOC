package com.accenture.lidlpoc.usecase
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.accenture.lidlpoc.utils.TestCoroutineRule
import com.accenture.lidlpoc.domain.api.LidlApi
import com.accenture.lidlpoc.domain.generics.LidlApiResult
import com.accenture.lidlpoc.domain.model.DataModel
import com.accenture.lidlpoc.domain.repository.LidlRepository
import com.accenture.lidlpoc.domain.usecase.GetCouponDetailUseCase
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCouponsDetailUseCaseTest {

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
    fun givenFlowData_whenInvokeMethod_shouldReturnDetailCoupon() {
        val getCouponDetailUseCase = GetCouponDetailUseCase(lidlRepository!!)
        val list = DataModel.getCouponDetail(1)
        val response_data = Response.success(list)

        testCoroutineRule.runBlockingTest {
            doReturn(response_data)
                .`when`(api)
                .getCouponDetail(1)
            val result = getCouponDetailUseCase.invoke(1)
            result.collect {result ->
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