package com.example.atul_.eatit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.example.atul_.eatit.Common.Common;
import com.example.atul_.eatit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.security.AccessController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn,fbtn;
    TextView btnSignUp;
    CheckBox ckbRemember;
    TextView txtForgotPwd;
    ProgressDialog progressDialog;
    DatabaseReference table_user;
    FirebaseDatabase database;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public final boolean validate() {
        boolean valid = true;

        String phone = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();


        if (phone.isEmpty()) {
            edtPhone.setError("enter a valid User Id");
            valid = false;
        } else {
            edtPhone.setError(null);
        }

        if (password.isEmpty()) {
            edtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        edtPassword = (EditText) findViewById(R.id.edtpassword);
        edtPhone = (EditText) findViewById(R.id.edtphone);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);
        txtForgotPwd = (TextView) findViewById(R.id.txtForgotPwd);
        btnSignUp = (TextView) findViewById(R.id.btnSignUp);


        Paper.init(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.sample_animation);


        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.forgot_password_layout);

                final MaterialEditText edtPhone = (MaterialEditText) findViewById(R.id.edtphone);
                final MaterialEditText edtEmail = (MaterialEditText) findViewById(R.id.edtMail);
                fbtn=(Button)findViewById(R.id.fbtn);

                fbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String sub = edtPhone.getText().toString();
                        String to = edtEmail.getText().toString();

                        Toast.makeText(SignIn.this, "Your request is accepted you will receive a mail!!", Toast.LENGTH_SHORT).show();
                        try {
                            GMailSender sender = new GMailSender("devanshimadhani20@gmail.com", "password");
                            sender.sendMail("This is Subject",
                                    "This is Body",
                                    "user@gmail.com",
                                    "user@yahoo.com");
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });






            }




        });




        btnSignIn.setAnimation(animation);
        btnSignIn.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInternet(getBaseContext())) {


                    if (ckbRemember.isChecked()) {
                        Paper.book().write(Common.USER_KEY, edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                    }

                    validate();
                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please wait");
                    mDialog.show();


                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString());


                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();

                                    table_user.removeEventListener(this);
                                } else {
                                    Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(SignIn.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(SignIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);

        if (user != null && pwd != null) {

            if (!user.isEmpty() && !pwd.isEmpty()) {
                Intent home = new Intent(SignIn.this, Home.class);

                startActivity(home);


            }


        }
    }
}



