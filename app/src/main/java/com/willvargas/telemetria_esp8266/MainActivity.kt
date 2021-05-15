package com.willvargas.telemetria_esp8266

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.willvargas.telemetria_esp8266.data.local.dao.UserDAO
import com.willvargas.telemetria_esp8266.data.local.entities.User
import com.willvargas.telemetria_esp8266.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val data = intent.extras
        val emailusuario = data?.getString("email")
        val nombreUsuario = data?.getString("nombre")

        val userDAO: UserDAO = MisUsuariosApp.database.UserDAO()
        val user: User = userDAO.searchUser(emailusuario.toString())

        val navigationView = this.findViewById<NavigationView>(R.id.nav_view)
        var tvEmailUsuario = navigationView.getHeaderView(0).findViewById<TextView>(R.id.textViewHeaderEmail)
        var tvNombreUsuario = navigationView.getHeaderView(0).findViewById<TextView>(R.id.textViewHeaderNombre)
        tvEmailUsuario.setText(emailusuario)
        tvNombreUsuario.setText(nombreUsuario)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_agregarEquipoFragment,
            R.id.nav_equipoFragment,
            ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
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
                val intent = Intent(this,LoginActivity::class.java)
                intent.putExtra("email",email)
                intent.putExtra("password",password)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}