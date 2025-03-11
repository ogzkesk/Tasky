package com.ogzkesk.database.network

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NetworkPagingSource<T : Any>(
    private val source: suspend (key: Int, size: Int) -> List<T>,
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val pageKey = params.key ?: 1
            val pageSize = params.loadSize

            val responseData = source.invoke(pageKey, pageSize)

            LoadResult.Page(
                data = responseData,
                prevKey = if (pageKey == 1) null else pageKey - 1,
                nextKey = if (responseData.isEmpty()) null else pageKey + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
