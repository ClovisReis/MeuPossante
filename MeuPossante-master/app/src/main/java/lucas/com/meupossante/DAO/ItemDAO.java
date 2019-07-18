package lucas.com.meupossante.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import lucas.com.meupossante.VO.ItemVO;

/**
 * Created by Lucas on 17/05/2016.
 */
public class ItemDAO extends AbstractDAO{
    public static final String TABLE_ITEM = "itens";
    public static final String TABLE_ITEM_COLUMN_ID = "_id";
    public static final String TABLE_ITEM_COLUMN_NAME = "nome";
    public static final String TABLE_ITEM_COLUMN_FREQUENCIA = "frequencia";
    public static final String TABLE_ITEM_COLUMN_ULTIMA_TROCA = "ultima_troca";

    public static final String CREATE_TABLE_ITEM = "create table "
            + TABLE_ITEM + "("
            + TABLE_ITEM_COLUMN_ID + " integer primary key autoincrement, "
            + TABLE_ITEM_COLUMN_NAME + " text not null, "
            + TABLE_ITEM_COLUMN_FREQUENCIA + " integer, "
            + TABLE_ITEM_COLUMN_ULTIMA_TROCA + " integer);";

    private String[] allColumns = {
            TABLE_ITEM_COLUMN_ID,
            TABLE_ITEM_COLUMN_NAME,
            TABLE_ITEM_COLUMN_FREQUENCIA,
            TABLE_ITEM_COLUMN_ULTIMA_TROCA
    };

    public ItemDAO(Context context) {
        super(context);
    }

    public long insert(ItemVO item){
        openWritable();
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_COLUMN_NAME, item.getName());
        values.put(TABLE_ITEM_COLUMN_FREQUENCIA, item.getFrequencia());
        values.put(TABLE_ITEM_COLUMN_ULTIMA_TROCA, item.getUltima_troca());
        long insertId = db.insert(TABLE_ITEM, null, values);
        close();
        return insertId;
    }

    public void update(ItemVO item){
        openWritable();
        ContentValues values = new ContentValues();
        values.put(TABLE_ITEM_COLUMN_NAME, item.getName());
        values.put(TABLE_ITEM_COLUMN_FREQUENCIA, item.getFrequencia());
        values.put(TABLE_ITEM_COLUMN_ULTIMA_TROCA, item.getUltima_troca());
        String whereClause = TABLE_ITEM_COLUMN_ID + " = ? ";
        String[] whereArgs = {item.getId().toString()};
        int count = db.update(TABLE_ITEM, values, whereClause, whereArgs);
        close();
    }

    public ItemVO findByName(ItemVO item){
        openReadable();
        String whereClause = TABLE_ITEM_COLUMN_NAME + " = ? ";
        String[] whereArgs = {item.getName()};
        Cursor cursor = db.query(TABLE_ITEM, allColumns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        item = cursorToItem(cursor);
        cursor.close();
        close();
        return item;
    }

    public List<ItemVO> findAll(){
        openReadable();
        List<ItemVO> itemList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_ITEM, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ItemVO itemVO = cursorToItem(cursor);
            itemList.add(itemVO);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return itemList;
    }

    private ItemVO cursorToItem(Cursor cursor){
        ItemVO item = new ItemVO();

        item.setId(cursor.getLong(cursor.getColumnIndex(TABLE_ITEM_COLUMN_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(TABLE_ITEM_COLUMN_NAME)));
        item.setFrequencia(cursor.getFloat(cursor.getColumnIndex(TABLE_ITEM_COLUMN_FREQUENCIA)));
        item.setUltima_troca(cursor.getFloat(cursor.getColumnIndex(TABLE_ITEM_COLUMN_ULTIMA_TROCA)));

        return item;
    }
}
