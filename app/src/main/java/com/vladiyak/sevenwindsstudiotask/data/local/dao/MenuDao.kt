package com.vladiyak.sevenwindsstudiotask.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.vladiyak.sevenwindsstudiotask.data.local.MenuItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {

    @Query("SELECT * FROM menu_item WHERE coffeeShopId == :coffeeShopId ORDER BY id ASC")
    fun observeByCoffeeShopId(coffeeShopId: Int): Flow<List<MenuItemEntity>>

    @Query("SELECT * FROM menu_item WHERE id IN (:idList)")
    fun observeByIds(idList: List<Int>): Flow<List<MenuItemEntity>>

    @Query("SELECT * FROM menu_item WHERE id = :id")
    suspend fun getById(id: Int): MenuItemEntity?

    @Upsert
    suspend fun upsert(menuItem: MenuItemEntity)

    @Upsert
    suspend fun upsert(menuItemList: List<MenuItemEntity>)

    @Query("DELETE FROM menu_item WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM menu_item")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(menuItemList: List<MenuItemEntity>) {
        deleteAll()
        upsert(menuItemList)
    }
}
