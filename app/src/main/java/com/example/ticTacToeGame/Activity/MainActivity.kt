package com.example.ticTacToeGame.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getColor
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.example.storybook.R
import com.example.ticTacToeGame.Fragment.MenuFragment
import com.google.android.gms.auth.account.WorkAccount.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.color.MaterialColors.getColor
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private lateinit var star1Image: ImageView
    private lateinit var star2Image: ImageView
    private lateinit var moonImage: ImageView
    private lateinit var sunnImage: ImageView
    private lateinit var menuButton: ImageButton

    private lateinit var menuFrame: FrameLayout
    private lateinit var menuFragment: MenuFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var checkAuthCount: String
    private var check by Delegates.notNull<Boolean>()

    private lateinit var toggle: ActionBarDrawerToggle

    lateinit var launcher: ActivityResultLauncher<Intent>
    lateinit var auth: FirebaseAuth

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        drawerLayout = findViewById(R.id.menu_draw)
        val navView: NavigationView = findViewById(R.id.nav_view)


        auth = Firebase.auth
        auth.currentUser
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException:: class.java)
                if(account != null){
                    firebaseAuthWithGoogle(account.idToken!!, navView)
                }
            }
            catch (ex: ApiException){
                println("ApiException")
            }
        }
        checkAuthState(navView)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.languages -> {
                    val inflater = layoutInflater
                    val builder = AlertDialog.Builder(navView.context)
                    val dialoglayout: View = inflater.inflate(R.layout.language_selector_dialog, null)
                    builder.setView(dialoglayout)
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(R.drawable.background3)

                    val dialogBtn = dialoglayout.findViewById<Button>(R.id.posSelectLangButton)
                    val dialogRusButton = dialoglayout.findViewById<RadioButton>(R.id.rusLangButton)
                    val dialogEngButton = dialoglayout.findViewById<RadioButton>(R.id.engLangButton)

                    dialogRusButton.setOnClickListener {
                        dialogEngButton.isChecked = false
                    }
                    dialogEngButton.setOnClickListener {
                        dialogRusButton.isChecked = false
                    }

                    dialogBtn.setOnClickListener{
                        if(dialogRusButton.isChecked){
                            Toast.makeText(this, "Будет рус", Toast.LENGTH_SHORT).show()
                        }
                        if(dialogEngButton.isChecked){
                            Toast.makeText(this, "Будет англ", Toast.LENGTH_SHORT).show()
                        }
                        dialog.dismiss()
                    }
                }
                R.id.history -> Toast.makeText(applicationContext, "История", Toast.LENGTH_SHORT).show()
                R.id.logout -> {
                    drawerLayout.close()
                    auth.signOut()
                    check = false
                    showNavigationView(navView)
                    drawerLayout.open()
                }
                R.id.auth -> {
                    drawerLayout.close()
                    singInWithGoogle()
                    drawerLayout.open()
                }
            }
            true
        }


        checkaDate()
        menuFragmentShow()

        hideBars()

        menuButton.setOnClickListener(menuListener)
    }

    private fun showNavigationView(navView: NavigationView){
        if(check){
            if(navView.getHeaderView(0) != null)
                navView.removeHeaderView(navView.getHeaderView(0))
            var header: View = LayoutInflater.from(this).inflate(R.layout.nav_header, null)
            navView.addHeaderView(header)
            var userName = navView.getHeaderView(0).findViewById<TextView>(R.id.user_name)
            userName.text = ""
            var userImageView = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.userImage)
            Picasso.get().load(auth.currentUser?.photoUrl).into(userImageView)
            userName.text = auth.currentUser?.displayName



            navView.menu.clear();
            navView.inflateMenu(R.menu.nav_menu)
        }
        else{
            if(navView.getHeaderView(0) != null)
                navView.removeHeaderView(navView.getHeaderView(0))
            val header: View = LayoutInflater.from(this).inflate(R.layout.nav_header_no_login, null)
            navView.addHeaderView(header)

            navView.menu.clear();
            navView.inflateMenu(R.menu.nav_menu_no_login);
        }

        navView.itemIconTintList = null
    }

    var menuListener: View.OnClickListener = View.OnClickListener{
        drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    private fun hideBars() {
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun menuFragmentShow(){

        menuFragment = MenuFragment()
        var fragmentTransaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(menuFrame.id, menuFragment)
        fragmentTransaction.commit()
    }

    private fun init(){
        star1Image = findViewById(R.id.starView1)
        star2Image = findViewById(R.id.starView2)
        moonImage = findViewById(R.id.moonView)
        sunnImage = findViewById(R.id.sunnView)
        menuFrame = findViewById(R.id.menuFrame)
        menuButton = findViewById(R.id.menuButton)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkaDate(){
        val date: String =
            SimpleDateFormat("HH").format(Calendar.getInstance().time)

        when(date){
            "21", "22", "23", "00", "01", "02", "03", "04", "05", "06", "07"
            -> {
                star1Image.visibility = View.VISIBLE
                star2Image.visibility = View.VISIBLE
                moonImage.visibility = View.VISIBLE
                sunnImage.visibility = View.INVISIBLE
            }
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
            -> {
                star1Image.visibility = View.INVISIBLE
                star2Image.visibility = View.INVISIBLE
                moonImage.visibility = View.INVISIBLE
                sunnImage.visibility = View.VISIBLE
            }
        }
    }

    private fun getClient(): GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, gso)
    }

    private fun singInWithGoogle(){
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String, navView: NavigationView){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                checkAuthState(navView)
            }
            else{
                check = false
                showNavigationView(navView)
            }
        }
    }

    private fun checkAuthState(navView: NavigationView){
        if(auth.currentUser != null){
            check = true
            showNavigationView(navView)
        }
        else{
            check = false
            showNavigationView(navView)
        }
    }

    private fun setupUserBar(){

    }
}

