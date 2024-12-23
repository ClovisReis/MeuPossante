package lucas.com.meupossante.DAO;

/**
 * Created by Lucas on 17/05/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "meupossante.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VeiculoDAO.CREATE_TABLE_VEICULO);
        db.execSQL(ItemDAO.CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + VeiculoDAO.TABLE_VEICULO);
        db.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_ITEM);
        onCreate(db);
    }

}