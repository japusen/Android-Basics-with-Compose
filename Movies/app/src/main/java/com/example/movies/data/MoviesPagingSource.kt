package com.example.movies.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.model.Movie
import com.example.movies.network.MoviesApiService

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val moviesApiService: MoviesApiService,
    private val requestType: RequestType,
    private val query: String = ""
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX

            val response = when(requestType) {
                RequestType.TOP_RATED ->
                    moviesApiService.getMovies(type = requestType.value, page = page)
                RequestType.POPULAR ->
                    moviesApiService.getMovies(type = requestType.value, page = page)
                RequestType.SEARCH ->
                    moviesApiService.movieSearch(query = query, page = page)

            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}