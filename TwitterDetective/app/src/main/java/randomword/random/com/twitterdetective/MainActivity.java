package randomword.random.com.twitterdetective;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String SEARCHES = "searches";
    private EditText queryEditText, tagEditText;
    private FloatingActionButton saveFloatingButton;
    private SharedPreferences savedSearches;
    private List<String> tags;
    private SearchesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveFloatingButton = findViewById(R.id.fab);
        saveFloatingButton.setOnClickListener(saveButtonListener);


        queryEditText = ((TextInputLayout) findViewById(R.id.inputLayout)).getEditText();
        queryEditText.addTextChangedListener(textWatcher);
        tagEditText = ((TextInputLayout) findViewById(R.id.tagInputLayout)).getEditText();
        tagEditText.addTextChangedListener(textWatcher);
        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);
        tags = new ArrayList<>(savedSearches.getAll().keySet());
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchesAdapter(itemClickListener, itemLongClickListener, tags);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };



    private final View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String query = queryEditText.getText().toString();
            String tag = tagEditText.getText().toString();
            if (!query.isEmpty() && !tag.isEmpty()) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                addTaggedSearch(tag, query);
                queryEditText.setText("");
                tagEditText.setText("");
                queryEditText.requestFocus();
            }
        }
    };

    private void addTaggedSearch(String tag, String query) {
        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
        preferencesEditor.putString(tag, query).apply();
        if (!tags.contains(tag)) {
            tags.add(tag);
            Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
            adapter.notifyDataSetChanged();
        }

    }

    private final View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tag = ((TextView) view).getText().toString();
            String urlString = getString(R.string.search_url) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            startActivity(webIntent);
        }
    };

    private View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            final String tag = ((TextView) view).getText().toString();

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.share_edit_delete_title, tag));
            builder.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            shareSearch(tag);
                            break;
                        case 1:
                            tagEditText.setText(tag);
                            queryEditText.setText(savedSearches.getString(tag, ""));
                            break;
                        case 2:
                            deleteSearch(tag);
                            break;
                    }
                }
            });
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.create().show();
            return true;
        }
    };

    private void shareSearch(String tag) {
        String urlString = getString(R.string.search_url) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message, urlString)).setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_search)));
    }

    private void deleteSearch(final String tag) {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);
        confirmBuilder.setMessage(getString(R.string.confirm_message, tag));
        confirmBuilder.setNegativeButton(getString(R.string.cancel), null);
        confirmBuilder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                tags.remove(tag);
                SharedPreferences.Editor preferencesEditor = savedSearches.edit();
                preferencesEditor.remove(tag);
                preferencesEditor.apply();
                adapter.notifyDataSetChanged();
            }
        });
        confirmBuilder.create().show();
    }

}