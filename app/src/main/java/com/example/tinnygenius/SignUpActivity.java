package com.example.tinnygenius;

import static com.example.tinnygenius.R.id.create_account_link;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
//Create an object of database reference class to access Firebase realtime database
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tinny-genius-default-rtdb.firebaseio.com/");
    EditText inputEmail, inputPassword, inputConfirmPassword,inputFullname, inputPhone;
    Button register;
    TextView alreadyHaveAccount;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//    String phonePattern = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

//    String[] validPhoneNumbers = {"2055550125","202 555 0125", "(202) 555-0125", "+111 (202) 555-0125", "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"};
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputFullname = findViewById(R.id.editTextNameAddress);
        inputEmail = findViewById(R.id.editTextEmail);
        inputPassword = findViewById(R.id.userPassword);
        inputConfirmPassword = findViewById(R.id.userPasswordConfirm);
        inputPhone = findViewById(R.id.editPhoneNumber);
        register = findViewById(R.id.buttonSU);
        alreadyHaveAccount=findViewById(R.id.already_account_link);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String userName = inputFullname.getText().toString();
        String phoneTxt = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPass = inputConfirmPassword.getText().toString();


        if ( phoneTxt.isEmpty() || phoneTxt.length() < 11 || phoneTxt.length() > 12 ) {
            inputPhone.setError("Enter a valid number");

        } else if(!email.matches(emailPattern)){
            inputEmail.setError("Enter a correct Email");

        } else if (password.isEmpty() || password.length()<8)
        {
            inputPassword.setError("Enter at least 8 alphanumeric keys for password");
        }
        else if (!password.equals(confirmPass))
        {
            inputConfirmPassword.setError("Password not matched");
        }
        else{
            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();

                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}