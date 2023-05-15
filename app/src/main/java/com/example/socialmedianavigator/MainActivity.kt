package com.example.socialmedianavigator

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.socialmedianavigator.databinding.MainActivityBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    private lateinit var binding : MainActivityBinding

    private var facebookPackageName     = "com.facebook.katana"
    private var twitterPackageName      = "com.twitter.android"
    private var instagramPackageName    = "com.instagram.android"
    private var redditPackageName       = "com.reddit.frontpage"
    private var gmailPackageName        = "com.google.android.gm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nameText.text = "Social Media Applications"

        binding.facebookBtn.setOnClickListener {
            openApp(facebookPackageName)
        }

        binding.twitterBtn.setOnClickListener {
            openApp(twitterPackageName)
        }

        binding.instagramBtn.setOnClickListener {
            openApp(instagramPackageName)
        }

        binding.redditBtn.setOnClickListener {
            openApp(redditPackageName)
        }

        binding.gmailBtn.setOnClickListener {
            openApp(gmailPackageName)
        }
    }

    private fun openApp(PackageName : String) {
        val builder = AlertDialog.Builder(this)
        val intent = packageManager.getLaunchIntentForPackage(PackageName)

        intent?.let {
            startActivity(it)
        } ?: run {


            builder.setTitle("Application not installed")
            builder.setMessage("Do you want to install the app?")

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                Toast.makeText(
                    this,
                    "You will be directed to the play store in seconds.",
                    Toast.LENGTH_LONG
                ).show()
                Timer().schedule(2000) {
                    installApplication(PackageName)
                    exitProcess(1)
                }
            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                Toast.makeText(this, "Do you want to open another app.", Toast.LENGTH_LONG).show()
            }


            builder.show()
        }

    }

    fun installApplication (PackageName : String){
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$PackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$PackageName")
                )
            )
        }
    }
}

