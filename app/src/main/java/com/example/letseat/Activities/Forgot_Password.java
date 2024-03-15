package com.example.letseat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.letseat.Model.ForgetPassword;
import com.example.letseat.Model.User;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Password extends AppCompatActivity {

    TextInputEditText email,pass,confirm_pass,otp;
    Button btn,btn2,btn3;
    String userOtp;
    String sendMail;
    String emailOtp;
    String generateOtp;
    RetrofitServices retrofitServices;
    UserApi userApi;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pg = new ProgressDialog(this);
        pg.setMessage("Sending Email");
        pg.setTitle("Loading");
        pg.setCanceledOnTouchOutside(true);
        setContentView(R.layout.activity_forgot_password);
        email=findViewById(R.id.Email);
        pass=findViewById(R.id.passsword);
        confirm_pass=findViewById(R.id.confirm_password);
        otp=findViewById(R.id.otp);
        btn=findViewById(R.id.forgot_btn);
        btn2=findViewById(R.id.forgot_btn1);
        btn3=findViewById(R.id.forgot_btn2);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getOTP();
                    btn.setVisibility(View.GONE);
                    btn2.setVisibility(View.VISIBLE);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP();
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.VISIBLE);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });

    }
    private void getOTP() throws MessagingException {
        String forgerOTP=otpEmail();
        if(email.getText().toString().length()==0){
            email.setError("* REQUIRED");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Email Must Be In Email Format");
        } else {
            pg.show();
            userApi.verifyEmailForForgetPass(email.getText().toString()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String code = String.valueOf(response.code());
                    if(code.equals("404")){
                        Toast.makeText(Forgot_Password.this, "Email Not Exist", Toast.LENGTH_SHORT).show();
                        pg.dismiss();
                    }else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if(sendPlainTextEmail("smtp.gmail.com","587","letseatpdm2024@gmail.com","rfwbmpzughmufojq",email.getText().toString(),"Forget Password",forgerOTP)){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        pg.dismiss();
                                                        Toast.makeText(Forgot_Password.this, "OTP Send On Your Email", Toast.LENGTH_SHORT).show();
                                                        otp.setVisibility(View.VISIBLE);

                                                    }
                                                });
                                                //Toast.makeText(Forgot_Password.this, "OTP Sent On Your Email", Toast.LENGTH_SHORT).show();
                                            }else {
                                                //Toast.makeText(Forgot_Password.this, "Email Is Not Register", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (MessagingException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });
                                t.start();
                            }
                        },3000);

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }

    }
    private void verifyOTP(){

        //btn.setText("Forget");
        if(userOtp.equals(otp.getText().toString())){
            pass.setVisibility(View.VISIBLE);
            confirm_pass.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(Forgot_Password.this, "Otp Invalid", Toast.LENGTH_SHORT).show();
        }
    }
    private void changePass(){
        if(pass.getText().toString().equals(confirm_pass.getText().toString())){
            userApi.updatePasswordByEmail(email.getText().toString(),pass.getText().toString()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(Forgot_Password.this, "Password Change Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(Forgot_Password.this, "Password And Confirm Password is Not Same", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean sendPlainTextEmail(String host, String port,
                                      final String userName, final String password, String toAddress,
                                      String subject, String message) throws RuntimeException, MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
// *** BEGIN CHANGE
        properties.put("mail.smtp.user", userName);

        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getDefaultInstance(properties);
// *** END CHANGE

        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);

// *** BEGIN CHANGE
        // sends the e-mail
        Transport t = session.getTransport("smtp");
        t.connect(userName, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
// *** END CHANGE
        return  true;
    }

    private String otpEmail(){
        otpGenaretor();
        emailOtp = "Subject: Password Reset OTP Request\n" +
                "\n" +
                "Dear "+email.getText().toString()+",\n" +
                "\n" +
                "We have received a request to reset the password associated with your account. As a security measure, we are sending you a One-Time Password (OTP) to verify your identity and proceed with the password reset process.\n" +
                "\n" +
                "Your OTP is:"+generateOtp+"\n" +
                "\n" +
                "Please use this OTP to reset your password. Once entered, you will be prompted to create a new password for your account. Please ensure that you keep this OTP confidential and do not share it with anyone.\n" +
                "\n" +
                "If you did not request this password reset or have any concerns about the security of your account, please contact our support team immediately at letleatpdm2024@gmail.com or 7096011908.\n" +
                "\n" +
                "Thank you for your cooperation.\n" +
                "\n" +
                "Best regards,\n" +
                "LET'S EAT Team\n" +
                "\n" +
                "letseatpdm2024@gmail.com";
        return  emailOtp;
    }
    private void otpGenaretor(){
        Random random = new Random();
        String o = String.format("%04d",random.nextInt(10000));
        generateOtp= o;
        userOtp=generateOtp;
    }
}