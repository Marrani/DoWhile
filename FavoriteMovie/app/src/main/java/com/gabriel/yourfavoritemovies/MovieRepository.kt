package com.gabriel.yourfavoritemovies

import com.gabriel.yourfavoritemovies.Constants.API_KEY

class MovieRepository {
    suspend fun getMovies(language: String) = MovieService.getApi().getApodDay(API_KEY, language)
}