package simpleToDo.simpleToDoList;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.simpleToDoList.R;

public class EditActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_EDITTEXT = "text";
    public static final String EXTRA_POSITION = "position";

    private EditText editTitleView;
    private EditText editText;
    android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mToolbar = findViewById(R.id.editActivityToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        editTitleView = findViewById(R.id.editTitle);
        editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String text = intent.getStringExtra(EXTRA_EDITTEXT);
        editTitleView.setText(title);
        editText.setText(text);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(EXTRA_TITLE, editTitleView.getText().toString());
        intent.putExtra(EXTRA_EDITTEXT, editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
