package fptu.prm.cookcook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import fptu.prm.cookcook.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private ActivitySignUpBinding mViewBinding;
    private FirebaseAuth mAuth;
    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);
        initView();
        initData();
        initControl();
    }

    private void initData() {

    }

    private void initControl() {
        mViewBinding.btnSignUp.setOnClickListener(view -> {
            mAuth = FirebaseAuth.getInstance();
            mEmail = Objects.requireNonNull(mViewBinding.edtEmail.getText()).toString().trim();
            mPassword = Objects.requireNonNull(mViewBinding.edtPassword.getText()).toString().trim();
            mConfirmPassword = Objects.requireNonNull(mViewBinding.edtConfirmPassword.getText()).toString().trim();
            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        });
    }

    private void initView() {

    }
}
