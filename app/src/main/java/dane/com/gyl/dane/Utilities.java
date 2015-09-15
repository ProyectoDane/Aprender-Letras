package dane.com.gyl.dane;

import android.view.View;

/**
 * Created by mbobadilla on 26/03/2015.
 */
public class Utilities {

    static float convertFromDp(int input, View view) {
        final float scale = view.getContext().getResources().getDisplayMetrics().density;
        return ((input - 0.5f) / scale);
    }

}
