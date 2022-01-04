package com.fefuproject.druzhbank.dirprofile

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fefuproject.druzhbank.databinding.ActivityProfileBinding
import com.fefuproject.druzhbank.di.AuthStateObserver
import com.fefuproject.druzhbank.dirprofile.dircard.Cards
import com.fefuproject.druzhbank.dirprofile.dircard.CardsAdapter
import com.fefuproject.druzhbank.dirprofile.dircredit.Credits
import com.fefuproject.druzhbank.dirprofile.dircredit.CreditsAdapter
import com.fefuproject.druzhbank.dirprofile.dirpay.Pays
import com.fefuproject.druzhbank.dirprofile.dirpay.PaysAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var authObserver: AuthStateObserver
    lateinit var binding: ActivityProfileBinding

    private val cardsAdapter=CardsAdapter()
    private val paysAdapter=PaysAdapter()
    private  val creditsAdapter= CreditsAdapter()


    private val credits=Credits(
        nameCredit = "Рассрочка на наушники",
        valueCredit = "15000 рублей",
        dateCredit = "Платеж 10.01.22"
    )
    private val credits1=Credits(
        nameCredit = "Ипотека",
        valueCredit = "15314444000 рублей",
        dateCredit = "Платеж 15.01.22"
    )


    private val pays= Pays(
        namePay = "Пенсия"
    , valuePay = "12000 рублей"
    , numberPay = "****9999"
    )
    private val pays1= Pays(
        namePay = "Накопления"
        , valuePay = "912000 рублей"
        , numberPay = "****9888"
    )


    private val cards= Cards(
        typeCard = "Кредитка",
        valueCard = "2140000 рублей"
        , whatBank ="mir",
        numberCard = "2145 1245 **** ****"
    )

    private val cards1= Cards(
        typeCard = "Зарплатная ",
        valueCard = "40000 рублей"
        , whatBank ="mir",
        numberCard = "3333 9999 **** ****"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        initDataRec()
        setContentView(binding.root)

    }
    private fun initDataRec(){
        binding.apply {

            cardsAdapter.addCard(cards)
            cardsAdapter.addCard(cards1)
            creditsAdapter.addCredit(credits)
            creditsAdapter.addCredit(credits1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            paysAdapter.addPay(pays)
            paysAdapter.addPay(pays1)
            recycleViewCards.layoutManager = LinearLayoutManager(this@ProfileActivity)
            recycleViewCards.adapter=cardsAdapter
            recycleViewCredits.layoutManager = LinearLayoutManager(this@ProfileActivity)
            recycleViewCredits.adapter=creditsAdapter
            recycleViewPay.layoutManager = LinearLayoutManager(this@ProfileActivity)
            recycleViewPay.adapter=paysAdapter


        }
    }

}