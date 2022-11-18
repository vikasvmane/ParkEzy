package com.whiterwalkers.parkezy.model.datasource.history

import com.whiterwalkers.parkezy.model.pojos.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockHistoryDataStoreImpl @Inject constructor() : HistoryDataStore {
    override fun getHistoryData(userId: Int): Flow<List<Transaction>> {
        return flow {
            emit(
                listOf(
                    Transaction(
                        "Theia",
                        "Eela society", "Tata Nexon MH23SE3432", "Credit Card", "40", 4f
                    ),
                    Transaction(
                        "Dream-It Furniture",
                        "Opp Siddhashila", "Honda City UK23DR3434", "Wallet", "80", 5f
                    )
                )
            )
        }
    }
}