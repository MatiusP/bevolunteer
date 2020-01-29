package com.haipclick.app.root.structure

import org.dzedzich.volunteers.root.structure.BaseRepository

/**
 * RSI - Retorfit Service Interface
 */
abstract class ServiceRepository<out T : Any>(RSI: Class<T>) : BaseRepository() {

     val service: T = retrofit.create(RSI)

}