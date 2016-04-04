package god.liu.yanpeng.calnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalNote extends AppCompatActivity {

    CalendarView calendar;
    Bundle sendDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_note);

        calendar= (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new onDateChangeListener());

    }

    class onDateChangeListener implements CalendarView.OnDateChangeListener{

        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            setTitle(year+"-"+month+"-"+dayOfMonth);
            sendDate=new Bundle();
            sendDate.putString("noteDate",year+"-"+month+"-"+dayOfMonth);

            Intent intent=new Intent();
            intent.setClass(CalNote.this,NoteBook.class);
            intent.putExtras(sendDate);

            startActivity(intent);
        }


    }

}
