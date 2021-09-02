package com.willvargas.telemetria_esp8266.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.willvargas.telemetria_esp8266.R
import com.willvargas.telemetria_esp8266.data.server.EquiposServer
import com.willvargas.telemetria_esp8266.databinding.FragmentAgregarEquipoBinding
import java.io.ByteArrayOutputStream

class AgregarEquipoFragment : Fragment() {

    private lateinit var agregarEquipoBinding: FragmentAgregarEquipoBinding
    private lateinit var auth: FirebaseAuth
    private val File = 1

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
        if ( result.resultCode == RESULT_OK){
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            agregarEquipoBinding.takePictureImageView.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agregarEquipoBinding = FragmentAgregarEquipoBinding.inflate(inflater, container, false)


        auth = Firebase.auth
        val idF = auth.currentUser?.uid
        val emailusuario = auth.currentUser?.email

        with(agregarEquipoBinding) {
            textViewUsuario.setText(emailusuario).toString()
            agregarImagenButton.setOnClickListener { buscarImagen() }
            takePictureImageView.setOnClickListener { tomarFoto() }
            guardarEquipo.setOnClickListener { subirFotoFirebase() }

            guardarEquipo.isEnabled = false
            textViewCount.addTextChangedListener {
                guardarEquipo.isEnabled =
                    (textViewCount.length() >= 1) and (textViewId.length() >= 2)
            }
        }
        return agregarEquipoBinding.root
    }

    private fun tomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    private fun clearview() {
        with(agregarEquipoBinding) {
            EditTextContact.setText(" ")
            EditTextPhone.setText(" ")
            EditTextAddress.setText(" ")
            textViewId.setText(" ")
            textViewCount.setText(" ")
            textViewNote.setText(" ")
            takePictureImageView.setImageBitmap(null)
        }
    }

    private fun buscarImagen() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, File)
    }

    private fun subirFotoFirebase() {
        val db = Firebase.firestore
        val document = db.collection("users").document(auth.currentUser?.email.toString())
            .collection("equipos").document()
        val id = document.id
        val storage = FirebaseStorage.getInstance()
        val pictureRef = storage.reference.child("equipos").child(id)

        agregarEquipoBinding.takePictureImageView.isDrawingCacheEnabled = true
        agregarEquipoBinding.takePictureImageView.buildDrawingCache()
        val bitmap = (agregarEquipoBinding.takePictureImageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = pictureRef.putBytes(data)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot,Task<Uri>>{ task->
            pictureRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                guardarFirebaseEquipo(task.result.toString())
            }
        }

    }

            private fun guardarFirebaseEquipo(url: String) {

                with(agregarEquipoBinding) {
                //with(agregarEquipoBinding) {agregarEquipoBinding.guardarEquipo.setOnClickListener {

                    val nombreContacto = EditTextContact.text.toString()
                    val telefonoContacto: String? = EditTextPhone.text.toString()
                    val direccion: String? = EditTextAddress.text.toString()
                    val idEquipo: String? = textViewId.text.toString()
                    val contadorBebidas: Long? = textViewCount.text.toString().toLong()
                    val descripcion: String? = textViewNote.text.toString()
                    val emailUsuario = auth.currentUser?.email

/*                        val equipo = Equipo(
                        id = NULL,
                        nombreContacto = nombreContacto,
                        telefonoContacto = telefonoContacto,
                        direccion = direccion,
                        idEquipo = idEquipo,
                        contadorBebidas = contadorBebidas,
                        descripcion = descripcion,
                        emailUsuario = emailUsuario,
                    )
                    val equipoDAO: EquipoDAO = MiBaseDeDatosApp.databaseEquipos.EquipoDAO()
                    equipoDAO.insertEquipo(equipo)
*/
                    val db = Firebase.firestore
                    val document = db.collection("equipos").document()
                    val id = document.id
                    val equipoServer = EquiposServer(
                        id = id,
                        nombreContacto = nombreContacto,
                        telefonoContacto = telefonoContacto,
                        direccion = direccion,
                        idEquipo = idEquipo,
                        contadorBebidas = contadorBebidas,
                        descripcion = descripcion,
                        emailUsuario = emailUsuario,
                        urlPicture = url,
                    )
                    db.collection("users").document(auth.currentUser?.email.toString())
                        .collection("equipos").document(id).set(equipoServer)
                        .addOnSuccessListener {
                            Toast.makeText(
                                getContext(),
                                getString(R.string.equipment_added_successfully),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                getContext(),
                                getString(R.string.equipment_not_added),
                                Toast.LENGTH_LONG
                            ).show()
                        }


                    clearview()
                }
                //}

            }



}

/* private fun  dispatchTakePictureIntent(){
     Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
         takePictureIntent.resolveActivity(requireActivity().packageManager)?.also{
             startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
         }
     }
 }
 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
         val imageBitmap = data?.extras?.get("data") as Bitmap
         agregarEquipoBinding.takePictureImageView.setImageBitmap(imageBitmap)
     }
 }
*/