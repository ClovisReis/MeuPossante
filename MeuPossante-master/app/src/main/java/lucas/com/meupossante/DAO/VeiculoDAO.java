package lucas.com.meupossante.DAO;

/**
 * Created by Lucas on 17/05/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import lucas.com.meupossante.VO.VeiculoVO;

public class VeiculoDAO extends AbstractDAO {

    public static final String TABLE_VEICULO = "veiculo";
    public static final String TABLE_VEICULO_COLUMN_ID = "_id";
    public static final String TABLE_VEICULO_COLUMN_NAME = "nome";
    public static final String TABLE_VEICULO_COLUMN_KM = "quilometragem";
    public static final String TABLE_VEICULO_COLUMN_ULTIMA_REVISAO = "ultima_revisao";

    public static final String CREATE_TABLE_VEICULO = "create table "
            + TABLE_VEICULO + "("
            + TABLE_VEICULO_COLUMN_ID + " integer primary key autoincrement, "
            + TABLE_VEICULO_COLUMN_NAME + " text not null, "
            + TABLE_VEICULO_COLUMN_KM + " integer, "
            + TABLE_VEICULO_COLUMN_ULTIMA_REVISAO + " integer);";

    private String[] allColumns = {
            TABLE_VEICULO_COLUMN_ID,
            TABLE_VEICULO_COLUMN_NAME,
            TABLE_VEICULO_COLUMN_KM,
            TABLE_VEICULO_COLUMN_ULTIMA_REVISAO
    };

    public VeiculoDAO(Context context) {
        super(context);
    }

    public long insert(VeiculoVO veiculo) {
        openWritable();
        ContentValues values = new ContentValues();
        values.put(TABLE_VEICULO_COLUMN_NAME, veiculo.getName());
        values.put(TABLE_VEICULO_COLUMN_KM, veiculo.getQuilometragem());
        values.put(TABLE_VEICULO_COLUMN_ULTIMA_REVISAO, veiculo.getUltima_revisao());
        long insertId = db.insert( TABLE_VEICULO, null, values);
        close();

        return insertId;
    }

    public void update(VeiculoVO veiculo) {
        openWritable();
        ContentValues values = new ContentValues();
        values.put(TABLE_VEICULO_COLUMN_NAME, veiculo.getName());
        values.put(TABLE_VEICULO_COLUMN_KM, veiculo.getQuilometragem());
        values.put(TABLE_VEICULO_COLUMN_ULTIMA_REVISAO, veiculo.getUltima_revisao());
        String whereClause = TABLE_VEICULO_COLUMN_ID + " = ? ";
        String[] whereArgs = {veiculo.getId().toString()};
        int count = db.update(TABLE_VEICULO, values, whereClause, whereArgs);
        close();
    }

    public VeiculoVO findById(VeiculoVO veiculo) {
        openReadable();
        String whereClause = TABLE_VEICULO_COLUMN_ID + " = ? ";
        String[] whereArgs = { veiculo.getId().toString() };
        Cursor cursor = db.query( TABLE_VEICULO, allColumns, whereClause, whereArgs, null, null, null );
        cursor.moveToFirst();
        veiculo = cursorToVeiculo(cursor);
        cursor.close();
        close();
        return veiculo;
    }

    public List<VeiculoVO> findAll() {
        openReadable();
        List<VeiculoVO> veiculoList = new ArrayList<>();
        Cursor cursor = db.query( TABLE_VEICULO, allColumns, null, null, null, null, null );
        cursor.moveToFirst();
        while ( ! cursor.isAfterLast()) {
            VeiculoVO veiculoVO = cursorToVeiculo(cursor);
            veiculoList.add(veiculoVO);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return veiculoList;
    }

    private VeiculoVO cursorToVeiculo(Cursor cursor) {

        VeiculoVO veiculo = new VeiculoVO();

        veiculo.setId( cursor.getLong( cursor.getColumnIndex(TABLE_VEICULO_COLUMN_ID) ) );
        veiculo.setName( cursor.getString(cursor.getColumnIndex(TABLE_VEICULO_COLUMN_NAME)));
        veiculo.setQuilometragem(cursor.getFloat(cursor.getColumnIndex(TABLE_VEICULO_COLUMN_KM)));
        veiculo.setUltima_revisao(cursor.getFloat(cursor.getColumnIndex(TABLE_VEICULO_COLUMN_ULTIMA_REVISAO)));

        return veiculo;
    }
}