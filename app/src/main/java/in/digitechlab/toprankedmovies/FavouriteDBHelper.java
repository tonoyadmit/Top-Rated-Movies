package in.digitechlab.toprankedmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 9/27/2017.
 */

public class FavouriteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movie.db";

    public FavouriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
                FavouriteMovies.FavoriteEntry.TABLE_FAVORITE + "(" +
                FavouriteMovies.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouriteMovies.FavoriteEntry.MOVIE_ID + " TEXT NOT NULL, " +
                FavouriteMovies.FavoriteEntry.TITLE + " TEXT NOT NULL, " +
                FavouriteMovies.FavoriteEntry.POSTER_PATH + " TEXT NOT NULL, " +
                FavouriteMovies.FavoriteEntry.OVERVIEW + " TEXT NOT NULL, " +
                FavouriteMovies.FavoriteEntry.BACKDROP + " TEXT NOT NULL, " +
                FavouriteMovies.FavoriteEntry.VOTE_AVERAGE + " TEXT NOT NULL, " +
                FavouriteMovies.FavoriteEntry.RELEASE_DATE + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                FavouriteMovies.FavoriteEntry.TABLE_FAVORITE);
        onCreate(sqLiteDatabase);
    }
}
