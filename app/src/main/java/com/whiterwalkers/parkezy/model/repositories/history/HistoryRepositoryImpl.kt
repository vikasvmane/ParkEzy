package com.whiterwalkers.parkezy.model.repositories.history

import com.whiterwalkers.parkezy.model.datasource.history.HistoryDataStore
import com.whiterwalkers.parkezy.model.pojos.Transaction
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val historyDataStore: HistoryDataStore) :
    HistoryRepository {
    override suspend fun getParkingHistory(userId: Int): List<Transaction> {
        val mutableList = mutableListOf<Transaction>()
        historyDataStore.getHistoryData(userId).collect { list ->
            mutableList.addAll(list)
        }
        return mutableList
    }
}