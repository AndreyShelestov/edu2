package randomword.random.com.simplemvc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {
    private Button buttonCounter1, buttonCounter2, buttonCounter3;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonCounter1 = findViewById(R.id.buttonCounter1);
        buttonCounter2 = findViewById(R.id.buttonCounter2);
        buttonCounter3 = findViewById(R.id.buttonCounter3);
        buttonCounter1.setOnClickListener(this);
        buttonCounter2.setOnClickListener(this);
        buttonCounter3.setOnClickListener(this);
        mPresenter = new Presenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCounter1:
                mPresenter.buttonClickOne();
                break;
            case R.id.buttonCounter2:
                mPresenter.buttonClickTwo();
                break;
            case R.id.buttonCounter3:
                mPresenter.buttonClickThree();
                break;
        }
    }



    @Override
    public void setButtonText(int buttonIndex, int value) {
        switch (buttonIndex) {
            case 1:
                buttonCounter1.setText("Количество = " + value);
                break;
            case 2:
                buttonCounter2.setText("Количество = " + value);
                break;
            case 3:
                buttonCounter3.setText("Количество = " + value);
                break;
        }
    }
}
