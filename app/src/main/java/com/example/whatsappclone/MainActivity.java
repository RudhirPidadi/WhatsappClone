package com.example.whatsappclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity
{
    private EditText LoginUsernameEditText;
    private EditText LoginPasswordEditText;
    private Button LoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        LoginUsernameEditText=findViewById(R.id.login_username_edit_text);
        LoginPasswordEditText=findViewById(R.id.login_password_edit_text);
        LoginButton=findViewById(R.id.login_button);
    }


    public void LogInTheUser(View view)
    {
        ParseUser.logInInBackground(LoginUsernameEditText.getText().toString(), LoginPasswordEditText.getText().toString(), new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if (parseUser != null) {
                alertDisplayer("Sucessful Login","Welcome back " + LoginUsernameEditText.getText().toString() + "!");
            } else {
                ParseUser.logOut();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    });
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


    public void NavigateToSignUpActivity(View view)
    {
        Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }
}
