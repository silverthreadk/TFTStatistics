package com.silverthread.tftstatistics.model

class CompositeItem {
    lateinit var statData: StatData
        private set

    lateinit var header: Header
        private set

    var isHeader = false
        private set

    companion object {
        fun withStatData(statData: StatData): CompositeItem {
            val composite = CompositeItem()
            composite.statData = statData
            return composite
        }
        fun withHeader(header: Header): CompositeItem {
            val composite = CompositeItem()
            composite.header = header
            composite.isHeader = true
            return composite
        }
    }
}