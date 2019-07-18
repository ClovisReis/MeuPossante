package lucas.com.meupossante.DAO;

/**
 * Created by Lucas on 17/05/2016.
 */
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDAO {

    protected SQLiteDatabase db;
    protected MySQLiteHelper mySQLiteHelper;

    public static final String COLUMN_ID = "_id";

    protected AbstractDAO(Context context) {
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    protected void openWritable() throws SQLException {
        db = mySQLiteHelper.getWritableDatabase();
    }

    protected void openReadable() throws SQLException {
        db = mySQLiteHelper.getReadableDatabase();
    }

    protected void close() {
        mySQLiteHelper.close();
    }
}

