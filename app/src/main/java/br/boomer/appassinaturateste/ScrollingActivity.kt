package br.boomer.appassinaturateste

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ScrollingActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //TODO TÓPICO 10
//            val sendIntent = Intent()
//            sendIntent.action = Intent.ACTION_SEND
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Compartilhamento implícito!")
//            sendIntent.type = "text/plain"
//            if (sendIntent.resolveActivity(packageManager) != null) {
//                startActivity(sendIntent)
//            }
            // FIM TÓPICO 10

            //TODO TÓPICO 11
            // Neste caso, não foi necessário o uso de permissões. Apenas a chamada da Intent
//            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
//                type = ContactsContract.RawContacts.CONTENT_TYPE
//            }
//            intent.apply {
//                putExtra(ContactsContract.Intents.Insert.EMAIL, "EMAIL_DO_FELIPE@EMAIL.COM")
//                putExtra(
//                    ContactsContract.Intents.Insert.EMAIL_TYPE,
//                    ContactsContract.CommonDataKinds.Email.TYPE_WORK
//                )
//                putExtra(ContactsContract.Intents.Insert.PHONE, "86999999999")
//                putExtra(
//                    ContactsContract.Intents.Insert.PHONE_TYPE,
//                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK
//                )
//            }
//            startActivity(intent)
            // FIM TÓPICO 11

            //TODO TÓPICO 13
            // O armazenamento externo é um local público e de fácil acesso portanto, deve-se ter cautela
            // sobre o que se grava/guarda dentro desde local.
            // dispatchTakePictureIntent()
            // FIM TÓPICO 13

            //TODO TÓPICO 14
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem ;)"), PICK_IMAGE_REQUEST)
            // FIM TÓPICO 14

            //TODO TÓPICO 15
//            val editor = getSharedPreferences("USER_TABLE", Context.MODE_PRIVATE).edit()
//            editor.putString("USER_TYPE", edtx_estrangeiro.text.toString())
//            editor.commit()
//            Toast.makeText(this@ScrollingActivity, "Salvo", Toast.LENGTH_LONG).show()
            // FIM TÓPICO 15

            //TODO TÓPICO 17
            // Usar este código em outra aplicação:
            // No Manifest:
            // <uses-permission android:name="your.namespace.permission.TEST" />
            // Remova o código acima para ter um erro de violação de permissão (no outro app)

            // Usar este código em outra aplicação:
            // No click do botão (MainActivity):
            Intent().apply {
                action = "com.boomer.permission.security.MyAction"
                addCategory("android.intent.category.DEFAULT")
                startActivity(this)
            }
            // FIM TÓPICO 17
        }

        //TODO TÓPICO 12
        val editor = getSharedPreferences("PREFS_APP", Context.MODE_PRIVATE).edit()
        editor.putString("chave_secreta_autogerada", "capitão_caverna")
        editor.commit()
        // FIM TÓPICO 12
    }

    private val REQUEST_IMAGE_CAPTURE = 1

    val REQUEST_TAKE_PHOTO = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    var mCurrentPhotoPath: String = ""

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE_REQUEST && resultCode === Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            // Verificação pendente da API 21
            // Environment.isExternalStorageEmulated(uri)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                text.setCompoundDrawablesWithIntrinsicBounds(null, BitmapDrawable(resources, bitmap), null, null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
