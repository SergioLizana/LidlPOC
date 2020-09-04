package com.accenture.lidlpoc.domain.model

object DataModel {
    var couponList: ArrayList<Coupon>
    var couponDetailList: ArrayList<CouponDetail>

    init {
        val coupon1 =
            Coupon(1, "Toda la fruta fresca", "1 dia para desbloquearse", "image", false, true)
        val coupon2 = Coupon(2, "Embutidos Realvalle", "Finaliza hoy", "image", true, false)
        val coupon3 = Coupon(3, "Productos Solevita", "Finaliza hoy", "image", false, false)
        val coupon4 = Coupon(4, "Guacamole", "Finaliza hoy", "image", true, false)
        val coupon5 =
            Coupon(5, "Alimento para perros en tarrina", "Finaliza hoy", "image", false, true)
        val coupon6 = Coupon(6, "Vino Rosado", "Finaliza hoy", "image", false, false)
        val coupon7 = Coupon(7, "Cerveza 5% vol", "Finaliza hoy", "image", true, false)
        val coupon8 = Coupon(8, "Snacks para perros", "Finaliza hoy", "image", true, false)

        val dummyList = ArrayList<Coupon>()
        dummyList.add(coupon1)
        dummyList.add(coupon2)
        dummyList.add(coupon3)
        dummyList.add(coupon4)
        dummyList.add(coupon5)
        dummyList.add(coupon6)
        dummyList.add(coupon7)
        dummyList.add(coupon8)
        couponList = dummyList

        val dummyCouponDetailList = ArrayList<CouponDetail>()
        val couponDetail1 = CouponDetail(
            1,
            dummyList[0].isActive,
            "Lidl",
            "Fruta Fresca",
            "Toda la fruta fresca"
        )
        val couponDetail2 = CouponDetail(
            2,
            dummyList[1].isActive,
            "RealValle",
            "Embutidos Realvalle",
            "Embutidos Realvalle rebajados de precio"
        )
        val couponDetail3 = CouponDetail(
            3,
            dummyList[2].isActive,
            "Solevita",
            "Productos Solevita",
            "Los mejores productos Solevita"
        )
        val couponDetail4 = CouponDetail(
            4,
            dummyList[3].isActive,
            "Lidl",
            "Guacamole",
            "El mejor guacamole del mercado"
        )
        val couponDetail5 = CouponDetail(
            5,
            dummyList[4].isActive,
            "Alimento Perros",
            "Alimento para perros en tarrina",
            "Cuida a tu perro"
        )
        val couponDetail6 = CouponDetail(
            6,
            dummyList[5].isActive,
            "Lidl",
            "Vino Rosado",
            "Vino Rosado para acompañar a tus comidas"
        )
        val couponDetail7 = CouponDetail(
            7,
            dummyList[6].isActive,
            "Lidl",
            "Cerveza Lidl",
            "Pack 24 cervezas a mitad de precio"
        )
        val couponDetail8 = CouponDetail(
            8,
            dummyList[7].isActive,
            "Lidl",
            "Snacks para perros",
            "Snacks para perros"
        )

        dummyCouponDetailList.add(couponDetail1)
        dummyCouponDetailList.add(couponDetail2)
        dummyCouponDetailList.add(couponDetail3)
        dummyCouponDetailList.add(couponDetail4)
        dummyCouponDetailList.add(couponDetail5)
        dummyCouponDetailList.add(couponDetail6)
        dummyCouponDetailList.add(couponDetail7)
        dummyCouponDetailList.add(couponDetail8)

        couponDetailList = dummyCouponDetailList

    }

    fun activateCoupon(couponId: Int) {
        couponList.find { it.id == couponId }?.isActive = true
    }

    fun cancelCoupon(couponId: Int) {
        couponList.find { it.id == couponId }?.isActive = false
    }

    fun getCouponDetail(couponId: Int): CouponDetail? = couponDetailList.find { it.id == couponId }

}