package simpleToDo.simpleToDoList;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.simpleToDoList.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    RecyclerView.LayoutManager layoutManager;
    NoteDataSource mNoteDataSource;
    NoteDbScheme elements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.note_recycler_view);
        recyclerView.addItemDecoration(new ItemDecorationAlbumColumns());
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        noteAdapter = new NoteAdapter(this);
        noteAdapter.setItemEditClickListener(new NoteAdapter.OnItemEditClickListener() {
            @Override
            public void onItemEditClicked(int pos) {
                Note note = noteAdapter.getItem(pos);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(EditActivity.EXTRA_EDITTEXT, note.getText());
                intent.putExtra(EditActivity.EXTRA_POSITION, pos);
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(noteAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Note note = new Note();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(EditActivity.EXTRA_EDITTEXT, note.getText());
                startActivityForResult(intent, 2);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            noteAdapter.deleteElement(0);
            TastyToast.makeText(this, "Удалено :)", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                noteAdapter.deleteElement(0);
                TastyToast.makeText(this, "Удалено :)", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

            default:
                return super.onContextItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_notes) {

        } else if (id == R.id.nav_delete) {
            noteAdapter.clearList();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == 2) {
            String title = data.getStringExtra(EditActivity.EXTRA_TITLE);
            String text = data.getStringExtra(EditActivity.EXTRA_EDITTEXT);
            Note note = new Note(title, text);
            if (note.getTitle().equals("") && note.getText().equals("")) {
                TastyToast.makeText(this, "Заметка не может быть пустой!", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
            } else {
                noteAdapter.addNote(note);
            }
        } else {
            String title = data.getStringExtra(EditActivity.EXTRA_TITLE);
            String text = data.getStringExtra(EditActivity.EXTRA_EDITTEXT);
            int position = data.getIntExtra(EditActivity.EXTRA_POSITION, -1);
            Note note = new Note(title, text);
            if (note.getTitle().equals("") && note.getText().equals("")) {
                TastyToast.makeText(this, "Заметка не может быть пустой!", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
            } else {
                noteAdapter.replaceNote(note, position);
            }
        }
    }

    private void clearList() {
        mNoteDataSource.deleteAllNotes();
        dataUpdated();
    }

    private void addElement() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertView = factory.inflate(R.layout.app_bar_main, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertView);
        builder.setTitle(R.string.title);
        builder.setNegativeButton(R.string.alert_cancel, null);
        builder.setPositiveButton(R.string.menu_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = (EditText) alertView.findViewById(R.id.editTitle);
                // если использовать findViewById без alertView то всегда будем получать
                // editText = null
                mNoteDataSource.addNote(editText.getText().toString());
                dataUpdated();
            }
        });
        builder.show();
    }

    private void editElement(int id) {
        mNoteDataSource.editNote(elements.getId(), "Edited");
        dataUpdated();
    }

    private void deleteElement(int id) {
//        mNoteDataSource.deleteNote(elements.getId());
        dataUpdated();
    }

    private void dataUpdated() {
        noteAdapter.notifyDataSetChanged();
    }
}
