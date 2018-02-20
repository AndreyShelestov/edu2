package randomword.random.com.ybat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

/**
 * Created by i7 on 19.02.2018.
 */

public class AlignActivity extends Activity implements View.OnClickListener {
    Button btnLeft, btnRight, btnCenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.align);

        btnLeft = findViewById(R.id.btnLeft);
        btnCenter = findViewById(R.id.btnCenter);
        btnRight = findViewById(R.id.btnRight);

        btnLeft.setOnClickListener(this);
        btnCenter.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnCenter:
                intent.putExtra("center", Gravity.CENTER);
                break;
            case R.id.btnLeft:
                intent.putExtra("left", Gravity.LEFT);
                break;
            case R.id.btnRight:
                intent.putExtra("right", Gravity.RIGHT);
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
