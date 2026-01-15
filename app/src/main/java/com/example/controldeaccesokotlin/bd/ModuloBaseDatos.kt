package com.example.controldeaccesokotlin.bd

import android.content.Context
import androidx.room.Room
import com.example.controldeaccesokotlin.dao.DaoHistorial
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
data class ModuloBaseDatos(val algo : String) {

    @Provides
    @Singleton
    fun proporcionaBaseDatos(@ApplicationContext contexto: Context): RoomBaseDatos {
        return Room.databaseBuilder(contexto,
            RoomBaseDatos::class.java,
            "mi_base_datos").build()
    }

    //2. Acceso al dao (getter del Dao) !! UN DAO POR ENTIDAD !!
    @Provides
    @Singleton
    fun proporcionaHistorialDao(bd: RoomBaseDatos): DaoHistorial {
        return bd.daoHistorial
    }

}
