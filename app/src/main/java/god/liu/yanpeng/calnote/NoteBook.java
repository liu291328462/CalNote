package god.liu.yanpeng.calnote;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteBook extends AppCompatActivity {

    Button noteBook_Button;
    EditText noteBook_EditText;
    Bundle receiveDate;
    String noteDate,noteContent;
    SQLiteDatabase noteDatabase;
    Cursor cursor;
    Boolean isHaveData=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_book);

        noteBook_Button= (Button) findViewById(R.id.noteSubmit);
        noteBook_EditText= (EditText) findViewById(R.id.noteInputET);

        receiveDate=getIntent().getExtras();
        noteDate=receiveDate.getString("noteDate");

        noteDatabase=openOrCreateDatabase("noteDatabase.db3", MODE_PRIVATE, null);

        noteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notebook (" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "note_date TEXT NOT NULL," +
                            "note_content TEXT NOT NULL);");

        cursor=noteDatabase.rawQuery("SELECT * FROM notebook WHERE note_date=\'"+noteDate+"\';",null);
        if(cursor.moveToNext()){
            noteBook_EditText.setText(cursor.getString(2));
            isHaveData=true;
        }else {
            isHaveData=false;
        }
        cursor.close();

        //Toast.makeText(this,noteDate,Toast.LENGTH_LONG).show();
        noteBook_Button.setOnClickListener(new onClickListener());

    }

    class onClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            noteContent=noteBook_EditText.getText().toString();
            if(isHaveData){
                noteDatabase.execSQL("UPDATE notebook SET note_content=\'"+noteContent+"\' WHERE note_date=\'"+noteDate+"\';");
                Toast.makeText(NoteBook.this, "记录已更新", Toast.LENGTH_SHORT).show();
            }else {
                noteDatabase.execSQL("INSERT INTO notebook " +
                                    "(note_date,note_content) VALUES" +
                                    "(\'"+noteDate+"\',\'"+noteContent+"\');");
                Toast.makeText(NoteBook.this, "记录已保存", Toast.LENGTH_SHORT).show();
            }
            noteDatabase.close();

            finish();

        }
    }
}
