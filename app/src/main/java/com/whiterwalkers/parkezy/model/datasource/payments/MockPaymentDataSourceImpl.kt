package com.whiterwalkers.parkezy.model.datasource.payments

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.model.pojos.Payment
import com.whiterwalkers.parkezy.ui.utils.DataStore
import com.whiterwalkers.parkezy.ui.utils.USER_SELECTED_PAYMENT
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockPaymentDataSourceImpl @Inject constructor(@ApplicationContext val appContext: Context) :
    PaymentsDataSource {
    // private val Context.dataStore by preferencesDataStore("user_preferences")
    override fun getPaymentOptions(): Flow<List<Payment>> {
        return flow {
            emit(
                listOf(
                    Payment(
                        paymentId = 1,
                        paymentName = "Paytm Wallet",
                        icon = R.drawable.ic_paytm,
                        balance = "1000",
                        isPrimary = checkIsPrimary(1)
                    ),
                    Payment(
                        paymentId = 2,
                        paymentName = "UPI",
                        icon = R.drawable.ic_upi,
                        isPrimary = checkIsPrimary(2)
                    ),
                    Payment(
                        paymentId = 3,
                        paymentName = "Credit Card",
                        icon = R.drawable.ic_credit_card,
                        isPrimary = checkIsPrimary(3)
                    )
                )
            )
        }
    }

    override suspend fun saveSelectedPayment(payment: Payment) {
//        val dataStore = appContext.dataStore
//        dataStore.edit {
//            it[stringPreferencesKey(USER_SELECTED_PAYMENT)] = Gson().toJson(payment)
//        }
        DataStore.saveSelectedPayment(payment)
    }

    private fun checkIsPrimary(id: Int) =
        DataStore.getSelectedPayment() != null && DataStore.getSelectedPayment()?.paymentId == id

}