package com.whiterwalkers.parkezy.model.datasource.history

import com.whiterwalkers.parkezy.model.pojos.Transaction
import kotlinx.coroutines.flow.Flow

interface HistoryDataStore {
    fun getHistoryData(userId: Int): Flow<List<Transaction>>
}