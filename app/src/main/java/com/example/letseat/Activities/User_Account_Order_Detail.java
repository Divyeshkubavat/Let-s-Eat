package com.example.letseat.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.letseat.R;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class User_Account_Order_Detail extends AppCompatActivity {

    CircleImageView Place_Order_Img,Order_Accept_Img,Order_Process_Img,Out_For_Delivery_Img,Delivered_Img;
    TextView Place_Order_Txt,Order_Accept_Txt,Order_Process_Txt,Out_For_Delivery_Txt,Delivered_Txt;
    View Place_Order_View,Order_Accept_View,Order_Process_View,Out_For_Delivery_View;

    int Order_Status = 3;
    Button User_Account_Order_Cancel_Button;

    TextView Timer;
    long mili;
    long count = 1000;
    int min = 28;
    int m ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_order_detail);
        Place_Order_Img = findViewById(R.id.Order_Track_Place_Order_Imageview);
        Order_Accept_Img = findViewById(R.id.Order_Track_Accept_Order_Imageview);
        Order_Process_Img=findViewById(R.id.Order_Track_Process_Order_Imageview);
        Out_For_Delivery_Img=findViewById(R.id.Order_Track_Out_For_Deliver_Imageview);
        Delivered_Img=findViewById(R.id.Order_Track_Delivered_Imageview);
        Place_Order_Txt=findViewById(R.id.Order_Track_Place_Order_Textview);
        Order_Accept_Txt=findViewById(R.id.Order_Track_Accept_Order_Textiew);
        Order_Process_Txt=findViewById(R.id.Order_Track_Process_Order_Textview);
        Out_For_Delivery_Txt=findViewById(R.id.Order_Track_Out_For_Deliver_Textview);
        Delivered_Txt=findViewById(R.id.Order_Track_Delivered_Textview);
        Place_Order_View=findViewById(R.id.Order_Track_Place_Order_Line);
        Order_Accept_View=findViewById(R.id.Order_Track_Accept_Order_Line);
        Order_Process_View=findViewById(R.id.Order_Track_Process_Order_Line);
        Out_For_Delivery_View=findViewById(R.id.Order_Track_Out_For_Deliver_Line);
        Timer = findViewById(R.id.User_Account_Order_Detail_Timer);
        User_Account_Order_Cancel_Button=findViewById(R.id.User_Account_Order_Cancel_Button);
        Time();
        Track();
    }
    public void Track() {
        if(Order_Status == 1)
        {
            Place_Order_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Place_Order_Img.setImageResource(R.drawable.baseline_done_2_24);
            Place_Order_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Place_Order_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setBorderColor(Color.parseColor("#281818"));
            Order_Accept_Img.setImageResource(R.drawable.baseline_brightness_1_24);
            Order_Accept_Txt.setTextColor(Color.parseColor("#281818"));
            Order_Accept_View.setBackgroundColor(Color.parseColor("#281818"));
            Delivered_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Delivered_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Delivered_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Out_For_Delivery_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_View.setBackgroundColor(Color.parseColor("#FFFFB300"));
            Order_Process_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Order_Process_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Order_Process_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Order_Process_View.setBackgroundColor(Color.parseColor("#FFFFB300"));
        } else if (Order_Status == 2) {
            Place_Order_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Place_Order_Img.setImageResource(R.drawable.baseline_done_2_24);
            Place_Order_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Place_Order_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Accept_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setBorderColor(Color.parseColor("#281818"));
            Order_Process_Img.setImageResource(R.drawable.baseline_brightness_1_24);
            Order_Process_Txt.setTextColor(Color.parseColor("#281818"));
            Order_Process_View.setBackgroundColor(Color.parseColor("#281818"));
            Delivered_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Delivered_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Delivered_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Out_For_Delivery_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_View.setBackgroundColor(Color.parseColor("#FFFFB300"));
        } else if (Order_Status==3) {
            Place_Order_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Place_Order_Img.setImageResource(R.drawable.baseline_done_2_24);
            Place_Order_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Place_Order_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Accept_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Process_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Process_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_Img.setBorderColor(Color.parseColor("#281818"));
            Out_For_Delivery_Img.setImageResource(R.drawable.baseline_brightness_1_24);
            Out_For_Delivery_Txt.setTextColor(Color.parseColor("#281818"));
            Out_For_Delivery_View.setBackgroundColor(Color.parseColor("#281818"));
            Delivered_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Delivered_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Delivered_Txt.setTextColor(Color.parseColor("#FFFFB300"));
        } else if (Order_Status==4) {
            Place_Order_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Place_Order_Img.setImageResource(R.drawable.baseline_done_2_24);
            Place_Order_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Place_Order_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Accept_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Process_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Process_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_Img.setImageResource(R.drawable.baseline_done_2_24);
            Out_For_Delivery_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Delivered_Img.setBorderColor(Color.parseColor("#281818"));
            Delivered_Img.setImageResource(R.drawable.baseline_brightness_1_24);
            Delivered_Txt.setTextColor(Color.parseColor("#281818"));
        } else if(Order_Status==5){
            Place_Order_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Place_Order_Img.setImageResource(R.drawable.baseline_done_2_24);
            Place_Order_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Place_Order_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Accept_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Accept_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Order_Process_Img.setImageResource(R.drawable.baseline_done_2_24);
            Order_Process_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Order_Process_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_Img.setImageResource(R.drawable.baseline_done_2_24);
            Out_For_Delivery_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
            Out_For_Delivery_View.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            Delivered_Img.setBorderColor(Color.parseColor("#FF4CAF50"));
            Delivered_Img.setImageResource(R.drawable.baseline_done_2_24);
            Delivered_Txt.setTextColor(Color.parseColor("#FF4CAF50"));
        }else
        {
            Place_Order_Img.setBorderColor(Color.parseColor("#281818"));
            Place_Order_Img.setImageResource(R.drawable.baseline_brightness_1_24);
            Place_Order_Txt.setTextColor(Color.parseColor("#281818"));
            Place_Order_View.setBackgroundColor(Color.parseColor("#281818"));
            Order_Accept_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Order_Accept_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Order_Accept_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Order_Accept_View.setBackgroundColor(Color.parseColor("#FFFFB300"));
            Delivered_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Delivered_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Delivered_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Out_For_Delivery_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Out_For_Delivery_View.setBackgroundColor(Color.parseColor("#FFFFB300"));
            Order_Process_Img.setBorderColor(Color.parseColor("#FFFFB300"));
            Order_Process_Img.setImageResource(R.drawable.baseline_brightness_3_24);
            Order_Process_Txt.setTextColor(Color.parseColor("#FFFFB300"));
            Order_Process_View.setBackgroundColor(Color.parseColor("#FFFFB300"));
        }
    }
    public void Time(){
        Calendar calendar = Calendar.getInstance();
        m = calendar.getTime().getMinutes();
        min = m - min;
        min = 15 - min;
        if(min > 15)
        {
            Toast.makeText(this, "Not Able To Cancel Order", Toast.LENGTH_SHORT).show();

        }else
        {
            mili = (long) min * 60 * 1000;
            new CountDownTimer(mili, count) {
                @Override
                public void onTick(long l) {
                    NumberFormat numberFormat = new DecimalFormat("00");
                    long min = (l / 60000) %60;
                    long sec = (l/1000)%60;
                    Timer.setText(numberFormat.format(min)+":"+numberFormat.format(sec));
                }
                @Override
                public void onFinish() {
                    User_Account_Order_Cancel_Button.setVisibility(View.GONE);
                }
            }.start();
        }

    }
}