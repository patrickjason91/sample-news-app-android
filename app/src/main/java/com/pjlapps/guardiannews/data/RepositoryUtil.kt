package com.pjlapps.guardiannews.data

fun getPageOffset(page: Int, pageSize: Int): Int {
    return (page - 1) * pageSize
}