package com.colin.threechat.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.colin.threechat.bean.User


@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao


    companion object {
        @VisibleForTesting
        private const val DATABASE_NAME = "tc-db"
        private var mInstance: AppDataBase? = null
        /* val MIGRATION_1_2: Migration = object : Migration(2, 3) {
             override fun migrate(database: SupportSQLiteDatabase) {
                 database.execSQL("alter table t_user modify column id Integer ")
             }
         }*/

        fun getInstance(context: Context): AppDataBase {
            if (null == mInstance)
                mInstance = Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                        .build()
            return mInstance!!
        }


    }


}