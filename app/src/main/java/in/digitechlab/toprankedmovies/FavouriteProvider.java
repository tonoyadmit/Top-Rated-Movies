package in.digitechlab.toprankedmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by DELL on 9/27/2017.
 */

public class FavouriteProvider extends ContentProvider {

    private static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FavouriteDBHelper favoriteDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavouriteMovies.CONTENT_AUTHORITY;
        matcher.addURI(authority, FavouriteMovies.FavoriteEntry.TABLE_FAVORITE,
                MOVIE);
        matcher.addURI(authority, FavouriteMovies.FavoriteEntry.TABLE_FAVORITE +
                "/#", MOVIE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        favoriteDbHelper = new FavouriteDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteDbHelper.getReadableDatabase().query(
                        FavouriteMovies.FavoriteEntry.TABLE_FAVORITE, projection,
                        selection, selectionArgs, null, null, sortOrder);
                return cursor;
            case MOVIE_WITH_ID:
                cursor = favoriteDbHelper.getReadableDatabase().query(
                        FavouriteMovies.FavoriteEntry.TABLE_FAVORITE, projection,
                        FavouriteMovies.FavoriteEntry._ID + " = ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null, null, sortOrder);
                return cursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                return FavouriteMovies.FavoriteEntry.CONTENT_DIR_TYPE;
            case MOVIE_WITH_ID:
                return FavouriteMovies.FavoriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                long _id = db.insert(FavouriteMovies.FavoriteEntry.TABLE_FAVORITE,
                        null, contentValues);
                if (_id > 0) {
                    returnUri = FavouriteMovies.FavoriteEntry.buildFavoriteUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        int numDeleted;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                numDeleted = db.delete(
                        FavouriteMovies.FavoriteEntry.TABLE_FAVORITE, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavouriteMovies.FavoriteEntry.TABLE_FAVORITE + "'");
                break;
            case MOVIE_WITH_ID:
                numDeleted = db.delete(FavouriteMovies.FavoriteEntry.TABLE_FAVORITE,
                        FavouriteMovies.FavoriteEntry._ID + " = ? ",
                        new String []{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavouriteMovies.FavoriteEntry.TABLE_FAVORITE + "'");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numDeleted;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        int numUpdated;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                numUpdated = db.update(FavouriteMovies.FavoriteEntry.TABLE_FAVORITE, contentValues,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numUpdated;
    }
}
