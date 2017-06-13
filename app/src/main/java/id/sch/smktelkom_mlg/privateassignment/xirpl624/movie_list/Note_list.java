package id.sch.smktelkom_mlg.privateassignment.xirpl624.movie_list;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Note_list extends ListFragment implements View.OnClickListener,
        AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {


    public static final String TAG = "Example";

    private DataNoteHelper mDataHelper;
    private SimpleCursorAdapter mCursorAdapter;

    public Note_list() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);


        view.findViewById(R.id.btn_add).setOnClickListener(this);

        mDataHelper = new DataNoteHelper(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = mDataHelper.getAll();

        String[] from = new String[]{"title"};
        int[] to = new int[]{android.R.id.text1};

        mCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, cursor, from, to,
                SimpleCursorAdapter.FLAG_AUTO_REQUERY);

        setListAdapter(mCursorAdapter);
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(getActivity(), addnoteActivty.class);
                startActivity(intent);
                break;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long id) {

        Intent intent = new Intent(getActivity(), addnoteActivty.class);
        intent.putExtra(addnoteActivty.EXTRA_ID, id);
        startActivity(intent);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long id) {

        Cursor cursor = (Cursor) mCursorAdapter.getItem(i);

        Log.d(TAG, "Title:" + cursor.getString(0));
        Log.d(TAG, "Content:" + cursor.getString(1));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure want to delete this note?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDataHelper.deleteById((int) id);
                mCursorAdapter.notifyDataSetChanged();
            }
        });
        builder.create().show();
        return false;
    }
}