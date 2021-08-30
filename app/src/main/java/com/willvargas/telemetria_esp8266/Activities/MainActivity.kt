package com.willvargas.telemetria_esp8266.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.willvargas.telemetria_esp8266.MiBaseDeDatosApp
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.R.id.textViewHeaderNombre
import com.willvargas.telemetria_esp8266.data.local.dao.UserDAO
import com.willvargas.telemetria_esp8266.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        auth= Firebase.auth
        val idF = auth.currentUser?.uid
        val emailusuario = auth.currentUser?.email
        //val nombreUsuario =

        //val dataIntent = intent.extras
        //val emailusuario = dataIntent?.getString("email")
        //val nombreUsuario = dataIntent?.getString("nombre")

        //val userDAO: UserDAO = MiBaseDeDatosApp.databaseUser.UserDAO()
        //userDAO.searchUser(userEmail)

        val navigationView = this.findViewById<NavigationView>(R.id.nav_view)
        val tvEmailUsuario = navigationView.getHeaderView(0).findViewById<TextView>(R.id.textViewHeaderEmail)
        val tvNombreUsuario = navigationView.getHeaderView(0).findViewById<TextView>(textViewHeaderNombre)

        tvEmailUsuario.text = emailusuario
        //(tvNombreUsuario.text = nombreUsuario

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_agregarEquipoFragment,
            R.id.nav_equipoFragment,
            R.id.nav_listFragment,
            R.id.nav_borrarEquipoFragment,
            ), drawerLayout)
        this.setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_opciones, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.logout_menu ->{
                //onBackPressed()  //retorna a login destruyendo esta actividad
                val data = intent.extras
                val email = data?.getString("email")
                val password = data?.getString("password")
                val intent = Intent(this, LoginActivity::class.java)

                FirebaseAuth.getInstance().signOut()

                val sp = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                with(sp!!.edit()) {
                    putString("active", "false")
                    apply()
                }

                intent.putExtra("email",email)
                intent.putExtra("password",password)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}