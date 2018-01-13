package randomword.random.com.simplemvc;

/**
 * Created by i7 on 20.12.2017.
 */

public class Presenter {
    private Model mModel;
    private MainActivity view;
    int newModelValue;

    public Presenter(MainActivity view) {
        this.mModel = new Model();
        this.view = view;
    }

    private int calcNewModelValue(int modelElementIndex) {
        int currentValue = mModel.getElementValueAtIndex(modelElementIndex);
        return currentValue + 1;
    }


    public void buttonClickOne() {
        newModelValue = calcNewModelValue(0);
        mModel.setElementValueAtIndex(0, newModelValue);
        view.setButtonText(1, newModelValue);
    }

    public void buttonClickTwo() {
        newModelValue = calcNewModelValue(1);
        mModel.setElementValueAtIndex(1, newModelValue);
        view.setButtonText(2, newModelValue);
    }

    public void buttonClickThree() {
        newModelValue = calcNewModelValue(2);
        mModel.setElementValueAtIndex(2, newModelValue);
        view.setButtonText(3, newModelValue);
    }
}
