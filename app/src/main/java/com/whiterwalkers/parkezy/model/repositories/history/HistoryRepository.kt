package com.whiterwalkers.parkezy.model.repositories.history

import com.whiterwalkers.parkezy.model.pojos.Transaction

interface HistoryRepository {
    suspend fun getParkingHistory(userId: Int): List<Transaction>
}