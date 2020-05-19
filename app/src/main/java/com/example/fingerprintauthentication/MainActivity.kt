package com.example.fingerprintauthentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val executor = Executors.newSingleThreadExecutor()
        val activity: AppCompatActivity = this
        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    runOnUiThread { textView.text = getString(R.string.operation_cancelled_by_user) }
                } else {
                    runOnUiThread {textView.text = getString(R.string.dismiss)}
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                runOnUiThread {textView.text = getString(R.string.recognized)}
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                runOnUiThread { textView.text = getString(R.string.invalid)}
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verification")
            .setNegativeButtonText("Back")
            .build()

        launchAuthentication.setOnClickListener { biometricPrompt.authenticate(promptInfo) }

    }

}


