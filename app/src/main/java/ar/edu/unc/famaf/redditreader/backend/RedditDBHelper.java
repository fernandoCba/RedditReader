package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by Fernando on 5/11/2016.
 */

public class RedditDBHelper extends SQLiteOpenHelper {
    private static final String DBName = "redditFernando.db";
    private static final String POST_TABLE = "posts";
    private static final String POST_TABLE_ID = "_id";
    private static final String POST_TABLE_AUTHOR = "author";
    private static final String POST_TABLE_TITLE = "title";
    private static final String POST_TABLE_COMMENTS = "num_comments";
    private static final String POST_TABLE_CREATED_ON = "created_on";
    private static final String POST_TABLE_IMAGE = "image";
    private static final String POST_TABLE_SUBREDDIT = "subreddit";

    private static final String IMAGE_TABLE = "images";
    private static final String IMAGE_TABLE_URL = "url";
    private static final String IMAGE_TABLE_BITMAP = "data";
    private static final int ACTUAL_VERSION = 1;

    private RedditDBHelper(Context context, int version) {
        super(context, DBName, null, version);
    }

    public RedditDBHelper(Context context) {
        this(context, ACTUAL_VERSION);
    }

    /**
     * Persists the Listing data into the data base.
     * If no listing data is not available, database is not changed.
     *
     * @param listing
     */
    public void persistListing(Listing listing) {
        if (listing == null)
            return;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POST_TABLE, null, null);
        for (PostModel p :
                listing.getPosts()) {
            ContentValues values = new ContentValues();
            values.put(POST_TABLE_AUTHOR, p.getAuthor());
            values.put(POST_TABLE_COMMENTS, p.getComments());
            values.put(POST_TABLE_CREATED_ON, p.getCreatedOn());
            values.put(POST_TABLE_IMAGE, p.getImage());
            values.put(POST_TABLE_TITLE, p.getTitle());
            values.put(POST_TABLE_SUBREDDIT, p.getSubreddit());
            db.insert(POST_TABLE, null, values);
        }

    }

    public List<PostModel> getTopPostsFromDB(int limit, int offset) {
        List<PostModel> list = new ArrayList<PostModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s limit %d offset %d", POST_TABLE, limit, offset), null);
        if (cursor.moveToFirst()) {
            do {
                PostModel post = new PostModel();
                post.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(POST_TABLE_AUTHOR)));
                post.setComments(cursor.getInt(cursor.getColumnIndexOrThrow(POST_TABLE_COMMENTS)));
                post.setCreatedOn(cursor.getLong(cursor.getColumnIndexOrThrow(POST_TABLE_CREATED_ON)));
                post.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(POST_TABLE_IMAGE)));
                post.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(POST_TABLE_TITLE)));
                post.setSubreddit(cursor.getString(cursor.getColumnIndexOrThrow(POST_TABLE_SUBREDDIT)));
                list.add(post);
            } while (cursor.moveToNext());
        }

        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createImageTableQuery = "CREATE TABLE `" + IMAGE_TABLE + "` ("
                + "`" + IMAGE_TABLE_URL + "`	TEXT PRIMARY KEY,"
                + "`" + IMAGE_TABLE_BITMAP + "` BLOB NOT NULL"
                + ");";
        String createPostTableQuery = "CREATE TABLE `" + POST_TABLE + "` ("
                + "`" + POST_TABLE_ID + "`	INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "`" + POST_TABLE_TITLE + "`	TEXT NOT NULL,"
                + "`" + POST_TABLE_AUTHOR + "`	TEXT NOT NULL,"
                + "`" + POST_TABLE_CREATED_ON + "` TEXT NOT NULL,"
                + "`" + POST_TABLE_COMMENTS + "` INTEGER NOT NULL,"
                + "`" + POST_TABLE_SUBREDDIT + "` TEXT NOT NULL,"
                + "`" + POST_TABLE_IMAGE + "` TEXT"
                + ");";

        db.execSQL(createImageTableQuery);
        db.execSQL(createPostTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + IMAGE_TABLE);
        this.onCreate(db);
    }

    public void persistImage(String s, Bitmap bitmap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        values.put(IMAGE_TABLE_BITMAP, stream.toByteArray());
        values.put(IMAGE_TABLE_URL, s);
        db.insertWithOnConflict(IMAGE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Bitmap getImage(URL url) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bitmap = null;
        Cursor cursor = db.query(
                IMAGE_TABLE,
                new String[]{IMAGE_TABLE_BITMAP},
                IMAGE_TABLE_URL + " = ?",
                new String[]{url.toString()},
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            byte[] rawImage = cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGE_TABLE_BITMAP));
            bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);
        }
        return bitmap;

    }
}
