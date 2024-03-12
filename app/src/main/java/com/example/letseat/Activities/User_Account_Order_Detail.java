package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letseat.Adapter.OrderProductAdapter;
import com.example.letseat.Adapter.PaymentAdapter;
import com.example.letseat.Adapter.paymentProductAdapter;
import com.example.letseat.Model.Order;
import com.example.letseat.Model.OrderProduct;
import com.example.letseat.Model.Payment;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class User_Account_Order_Detail extends AppCompatActivity {

    CircleImageView Place_Order_Img,Order_Accept_Img,Order_Process_Img,Out_For_Delivery_Img,Delivered_Img;
    TextView Place_Order_Txt,Order_Accept_Txt,Order_Process_Txt,Out_For_Delivery_Txt,Delivered_Txt;
    View Place_Order_View,Order_Accept_View,Order_Process_View,Out_For_Delivery_View;


    RecyclerView User_Account_Order_Detail_Recyclerview;

    int Order_Status = 3;
    Button User_Account_Order_Cancel_Button,User_Account_Order_Detail_RatingBar_Submit_Button;
    RatingBar User_Account_Order_Detail_RatingBar;
    TextView Timer;
    RetrofitServices retrofitServices;
    UserApi userApi;
    long mili;
    long count = 1000;
    int m ;
    OrderProductAdapter adapter;
    ArrayList<OrderProduct> list;
    SharedPreferences preferences;
    ProgressDialog pg;
    TextView User_Account_Order_Detail_Order_ID,User_Account_Order_Detail_Toatl;
    int orderId;
    int rate;
    LinearLayout ratingTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_order_detail);
         preferences= getSharedPreferences("Date",MODE_PRIVATE);
        User_Account_Order_Detail_Recyclerview=findViewById(R.id.User_Account_Order_Detail_Recyclerview);
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
        ratingTextview=findViewById(R.id.l3);
        User_Account_Order_Detail_Order_ID=findViewById(R.id.User_Account_Order_Detail_Order_ID);
        User_Account_Order_Detail_Toatl=findViewById(R.id.User_Account_Order_Detail_Toatl);
        User_Account_Order_Detail_RatingBar_Submit_Button=findViewById(R.id.User_Account_Order_Detail_RatingBar_Submit_Button);
        User_Account_Order_Detail_RatingBar=findViewById(R.id.User_Account_Order_Detail_RatingBar);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        Timer = findViewById(R.id.User_Account_Order_Detail_Timer);
        User_Account_Order_Cancel_Button=findViewById(R.id.User_Account_Order_Cancel_Button);
        list=new ArrayList<>();
        setData();
        Time();
        pg = new ProgressDialog(User_Account_Order_Detail.this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait Deleting Order ....");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        getData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },500);
        User_Account_Order_Cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentDelete();
                pg = new ProgressDialog(User_Account_Order_Detail.this);
                pg.setTitle("Loading..... ");
                pg.setMessage("Please wait we fetch your data... ");
                pg.setCanceledOnTouchOutside(false);
                pg.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        orderDelete();
                        pg.cancel();
                        finish();
                    }
                },2000);
            }
        });

        User_Account_Order_Detail_RatingBar_Submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int rate = (int) User_Account_Order_Detail_RatingBar.getRating();
                    Order o = new Order();
                    o.setRating(rate);
                    userApi.updateOrder(orderId,o).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Toast.makeText(User_Account_Order_Detail.this, "Thank You For Rating", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });

            }
        });

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
    public void Time() {
        mili = 5 * 60 * 1000 - mili;
        if (mili >= 5*60*1000) {
            User_Account_Order_Cancel_Button.setVisibility(View.GONE);
        } else {
            User_Account_Order_Cancel_Button.setVisibility(View.VISIBLE);
        new CountDownTimer(mili, count) {
            @Override
            public void onTick(long l) {
                NumberFormat numberFormat = new DecimalFormat("00");
                long min = (l / 60000) % 60;
                long sec = (l / 1000) % 60;
                Timer.setText(numberFormat.format(min) + ":" + numberFormat.format(sec));

            }
            @Override
            public void onFinish() {
                User_Account_Order_Cancel_Button.setVisibility(View.GONE);
            }
        }.start();
      }
    }

    private void setData(){
        Intent i = getIntent();
        orderId= i.getIntExtra("oid",0);
        Order_Status = Integer.parseInt(String.valueOf(i.getIntExtra("status",0)));
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        Track();
        userApi.getSingleOrder(orderId).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                String finalTotal = String.valueOf(response.body().getFinalPayment());
                User_Account_Order_Detail_Toatl.setText("₹"+finalTotal);
                rate=response.body().getRating();
                if(rate==0){
                    ratingTextview.setVisibility(View.VISIBLE);
                    User_Account_Order_Detail_RatingBar_Submit_Button.setVisibility(View.VISIBLE);
                    User_Account_Order_Detail_RatingBar.setVisibility(View.VISIBLE);
                }else{
                    ratingTextview.setVisibility(View.GONE);
                    User_Account_Order_Detail_RatingBar_Submit_Button.setVisibility(View.GONE);
                    User_Account_Order_Detail_RatingBar.setVisibility(View.GONE);
                }
                String date=response.body().getDate();
                Date d = new Date(date);
                int min=d.getMinutes();
                mili=isAfterFiveMinutes(d);
                Time();
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
        User_Account_Order_Detail_Order_ID.setText("# "+String.valueOf(orderId));
    }
    private void getData(){
        Intent i = getIntent();
        int orderId= i.getIntExtra("oid",0);
        userApi.getSingleOrder(orderId).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                list= (ArrayList<OrderProduct>) response.body().getOrderProducts();
                adapter=new OrderProductAdapter(list,User_Account_Order_Detail.this);
                User_Account_Order_Detail_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Account_Order_Detail.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                User_Account_Order_Detail_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
    public  long isAfterFiveMinutes(Date orderPlacementTime) {
        // Get the current time
        Date currentTime = new Date();

        // Calculate the difference in milliseconds between current time and order placement time
        long timeDifferenceInMillis = currentTime.getTime() - orderPlacementTime.getTime();

        // Convert the difference to minutes
        //long minutesDifference = timeDifferenceInMillis / (60 * 1000);


        // Check if the difference is greater than or equal to 5 minutes
        return timeDifferenceInMillis;
    }

    private void paymentDelete(){
        userApi.deletePaymentByOrderId(orderId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void orderDelete() {
        userApi.deleteByOrderId(orderId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(User_Account_Order_Detail.this, "Order Deleted Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onResume();
        setData();
        getData();
        Track();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
        getData();
        Track();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}