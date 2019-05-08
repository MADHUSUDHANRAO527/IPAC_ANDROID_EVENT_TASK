package com.bosch.ipactask.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Window;
import android.widget.CalendarView;

import com.bosch.ipactask.R;
import com.bosch.ipactask.db.utils.DatabaseInitializer;
import com.bosch.ipactask.db.utils.EventListViewModel;
import com.bosch.ipactask.events.DateTriggerEvent;


public class ViewDialog {

    public void showDialog(Activity activity) {
        EventListViewModel viewModel = new EventListViewModel();
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_calender);
        CalendarView simpleCalendarView = (CalendarView) dialog.findViewById(R.id.simpleCalendarView);

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                viewModel.getSelectedDate().setValue("dayOfMonth + \"-\" + month + \"-\" + year");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
