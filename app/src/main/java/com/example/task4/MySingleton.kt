package com.example.task4

class MySingleton {
    companion object {
        private var instance: MySingleton? = null

        fun getInstance(): MySingleton {
            return instance ?: synchronized(this) {
                instance ?: MySingleton().also {
                    instance = it
                }
            }
        }
    }

    fun getDataList(): List<Data> {
        return itemList()
    }
}