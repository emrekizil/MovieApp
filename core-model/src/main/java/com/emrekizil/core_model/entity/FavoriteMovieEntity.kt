package com.emrekizil.core_model.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emrekizil.core_model.MovieDetail

@Entity
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val overview: String,
    val backdropPath: String,
    val genres: List<String>,
    val voteAverage: Double,
    val title: String,
    val releaseDate: String,
) {
    fun toMovieDetail() : MovieDetail {
        return MovieDetail(
            this.id,
            this.overview,
            this.backdropPath,
            this.genres,
            this.voteAverage,
            this.title,
            this.releaseDate
        )
    }
}
