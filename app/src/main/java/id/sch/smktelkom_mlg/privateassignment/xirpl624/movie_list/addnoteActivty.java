package id.sch.smktelkom_mlg.privateassignment.xirpl624.movie_list;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

public class addnoteActivty extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";

    private EditText inpTitle;
    private EditText inpContent;

    private DataNoteHelper mDataHelper;

    private long mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note);

/*

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
*/
        inpTitle = (EditText) findViewById(R.id.title);
        inpContent = (EditText) findViewById(R.id.content);

        mDataHelper = new DataNoteHelper(this);

        // cek apakah add atau edit
        mId = getIntent().getLongExtra(EXTRA_ID, 0);
        if (mId != 0) {
            Cursor cursor = mDataHelper.getById((int) mId);
            if (cursor.moveToFirst()) {
                do { // untuk contoh kalau data banyak
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    // masukkan ke view
                    inpTitle.setText(title);
                    inpContent.setText(content);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        // cek apakah insert atau update
        if (mId == 0)
            mId = mDataHelper.insert(inpTitle.getText().toString(), inpContent.getText().toString());
        else
            mDataHelper.updateByIdRawQuery((int) mId, inpTitle.getText().toString(), inpContent
                    .getText().toString());
        super.onPause();
    }

}
