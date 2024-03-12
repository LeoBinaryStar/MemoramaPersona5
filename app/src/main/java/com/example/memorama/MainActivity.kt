package com.example.memorama

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    //<editor-fold desc="IMAGENES GUI">
    lateinit var iv_11: ImageView
    lateinit var iv_12: ImageView
    lateinit var iv_13: ImageView
    lateinit var iv_14: ImageView

    lateinit var iv_21: ImageView
    lateinit var iv_22: ImageView
    lateinit var iv_23: ImageView
    lateinit var iv_24: ImageView

    lateinit var iv_31: ImageView
    lateinit var iv_32: ImageView
    lateinit var iv_33: ImageView
    lateinit var iv_34: ImageView

    //</editor-fold>

    //<editor-fold desc="OTROS GUI">
    lateinit var tv_j1: TextView
    lateinit var tv_j2: TextView

    lateinit var ib_sonido:ImageButton

    lateinit var mp: MediaPlayer
    lateinit var mpFondo: MediaPlayer
    lateinit var imagen1: ImageView
    lateinit var imagen2: ImageView


    //</editor-fold>

    //<editor-fold desc="VARIABLES">

    // Estas son las primeras imagenes
        var imagenesArray = arrayOf(11,12,13,14,15,16,21,22,23,24,25,26)

        var joker = 0
        var oracle = 0
        var panther = 0
        var noir = 0
        var skull = 0
        var queen = 0

        var turno = 1
        var puntosj1 = 0
        var puntosj2 = 0
        var numeroImagen = 1
        private var escuchar  = true

    //</editor-fold>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enlazarGUI()
    }

    private fun enlazarGUI() {
        iv_11 = findViewById(R.id.iv_11)
        iv_12 = findViewById(R.id.iv_12)
        iv_13 = findViewById(R.id.iv_13)
        iv_14 = findViewById(R.id.iv_14)
        iv_21 = findViewById(R.id.iv_21)
        iv_22 = findViewById(R.id.iv_22)
        iv_23 = findViewById(R.id.iv_23)
        iv_24 = findViewById(R.id.iv_24)
        iv_31 = findViewById(R.id.iv_31)
        iv_32 = findViewById(R.id.iv_32)
        iv_33 = findViewById(R.id.iv_33)
        iv_34 = findViewById(R.id.iv_34)

        ib_sonido = findViewById(R.id.ib_sonido)
        ib_sonido.setColorFilter(Color.GREEN)
        sonido("background",true)

        // Nos va a servir a la hora de barajar para el orden
        iv_11.tag = "0"
        iv_12.tag = "1"
        iv_13.tag = "2"
        iv_14.tag = "3"
        iv_21.tag = "4"
        iv_22.tag = "5"
        iv_23.tag = "6"
        iv_24.tag = "7"
        iv_31.tag = "8"
        iv_32.tag = "9"
        iv_33.tag = "10"
        iv_34.tag = "11"

        joker = R.drawable.joker
        oracle = R.drawable.oracle
        panther = R.drawable.panther
        noir = R.drawable.noir
        skull = R.drawable.skull
        queen = R.drawable.queen


        // Metodo para barajar
        imagenesArray.shuffle()

        //Inicializar los textView

        tv_j1 = findViewById(R.id.tv_j1)
        tv_j2 = findViewById(R.id.tv_j2)

        tv_j2.setTextColor(Color.GRAY)
        tv_j1.setTextColor(Color.WHITE)


    }

    private fun sonido(sonidoName: String,loop:Boolean= false) {
        var resID = resources.getIdentifier(sonidoName,"raw",packageName)

        if(sonidoName == "background"){
            mpFondo = MediaPlayer.create(this,resID)
            mpFondo.isLooping = loop
            mpFondo.setVolume(0.08F,0.08F)

            if(!mpFondo.isPlaying){
                mpFondo.start()
            }

        } else{
            
            this.mp = MediaPlayer.create(this,resID)
            this.mp.setOnCompletionListener(MediaPlayer.OnCompletionListener { mp: MediaPlayer? ->
                mp?.run {
                    stop()
                    release()
                }

            })

            if(!mp.isPlaying){
                mp.start()
            }
           
          

        }

    }// Fin de la funcion sonido

    fun musicaFondo(v:View){

        if(escuchar) {
            mpFondo.pause()
            ib_sonido.setImageResource(R.drawable.volumen_off)
            ib_sonido.setColorFilter(Color.GRAY)
        } else{
            mpFondo.start()
            ib_sonido.setImageResource(R.drawable.ic_volumen_on)
            ib_sonido.setColorFilter(Color.GREEN)
        }

        escuchar =! escuchar


    } // termina funcion musica de fondo

    fun seleccionar(imagen: View){
        sonido("touch")
        verificar(imagen)
    }

    private fun verificar(imagen: View) {
        // El view tiene la imagen en la que se hizo click
        val iv = (imagen as ImageView)
        // El tag representa la posicion del arreglo
        val tag  = imagen.tag.toString().toInt()

        if(imagenesArray[tag] == 11){
            iv.setImageResource(joker)
        } else if(imagenesArray[tag] == 12){
            iv.setImageResource(oracle)
        } else if (imagenesArray[tag] == 13){
            iv.setImageResource(panther)
        } else if (imagenesArray[tag] == 14){
            iv.setImageResource(noir)
        } else if(imagenesArray[tag] == 15){
            iv.setImageResource(skull)
        }  else if(imagenesArray[tag] == 16) {
            iv.setImageResource(queen)
        } else if(imagenesArray[tag] == 21){
            iv.setImageResource(joker)
        } else if (imagenesArray[tag] == 22){
            iv.setImageResource(oracle)
        } else if (imagenesArray[tag] == 23){
            iv.setImageResource(panther)
        } else if(imagenesArray[tag] == 24){
            iv.setImageResource(noir)
        } else if(imagenesArray[tag] == 25){
            iv.setImageResource(skull)
        } else if(imagenesArray[tag] == 26){
            iv.setImageResource(queen)
        }

        // guardar temporalmente imagen seleccionada

        // Si es la primera imagen seleccionada
        if(numeroImagen == 1){
            //imagen 1 es igual a la imagen seleccionada
            imagen1 = iv
            numeroImagen = 2
            iv.isEnabled = false
        } else if (numeroImagen == 2) {
            imagen2 = iv
            numeroImagen = 1
            iv.isEnabled = false
            desahabilitarImagenes()
            val h = Handler(Looper.getMainLooper())
            h.postDelayed({sonImagenesIguales()},1000)
        }



    } //  fin del metodo verificar

    private fun desahabilitarImagenes() {
        iv_11.isEnabled = false
        iv_12.isEnabled = false
        iv_13.isEnabled = false
        iv_14.isEnabled = false
        iv_21.isEnabled = false
        iv_22.isEnabled = false
        iv_23.isEnabled = false
        iv_24.isEnabled = false
        iv_31.isEnabled = false
        iv_32.isEnabled = false
        iv_33.isEnabled = false
        iv_34.isEnabled = false

    }

    @SuppressLint("SetTextI18n")
    private fun sonImagenesIguales() {


        // Compara las caractertisiticas de las imagenes
        if(imagen1.drawable.constantState == imagen2.drawable.constantState ){
            sonido("yes")

            if(turno == 1){
                puntosj1 += 1
                tv_j1.text = "J1:$puntosj1"
            } else if(turno == 2){
                puntosj2 += 1
                tv_j2.text = "J2:$puntosj2"
            }

            imagen1.isEnabled = false
            imagen2.isEnabled = false
            imagen1.tag = ""
            imagen2.tag = ""

        } else{
            sonido("nop")
            imagen1.setImageResource(R.drawable.img_card)
            imagen2.setImageResource(R.drawable.img_card)

            if(turno == 1){
                turno = 2
                tv_j1.setTextColor(Color.GRAY)
                tv_j2.setTextColor(Color.WHITE)
            } else if (turno == 2){
                turno = 1
                tv_j1.setTextColor(Color.WHITE)
                tv_j2.setTextColor(Color.GRAY)
            }

        }

        iv_11.isEnabled = iv_11.tag.toString().isNotEmpty()
        iv_12.isEnabled = iv_12.tag.toString().isNotEmpty()
        iv_13.isEnabled = iv_13.tag.toString().isNotEmpty()
        iv_14.isEnabled = iv_14.tag.toString().isNotEmpty()
        iv_21.isEnabled = iv_21.tag.toString().isNotEmpty()
        iv_22.isEnabled = iv_22.tag.toString().isNotEmpty()
        iv_23.isEnabled = iv_23.tag.toString().isNotEmpty()
        iv_24.isEnabled = iv_24.tag.toString().isNotEmpty()
        iv_31.isEnabled = iv_31.tag.toString().isNotEmpty()
        iv_32.isEnabled = iv_32.tag.toString().isNotEmpty()
        iv_33.isEnabled = iv_33.tag.toString().isNotEmpty()
        iv_34.isEnabled = iv_34.tag.toString().isNotEmpty()

        verificarFinJuego()

    }

    private fun verificarFinJuego() {
        if(
            iv_11.tag.toString().isEmpty() &&
            iv_12.tag.toString().isEmpty() &&
            iv_13.tag.toString().isEmpty() &&
            iv_14.tag.toString().isEmpty() &&
            iv_21.tag.toString().isEmpty() &&
            iv_22.tag.toString().isEmpty() &&
            iv_23.tag.toString().isEmpty() &&
            iv_24.tag.toString().isEmpty() &&
            iv_31.tag.toString().isEmpty() &&
            iv_32.tag.toString().isEmpty() &&
            iv_33.tag.toString().isEmpty() &&
            iv_34.tag.toString().isEmpty()


        ){
            mp.stop()
            mp.release()
            sonido("win")
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("FIN DEL JUEGO")
                .setMessage("PUNTAJE \nJ1:" + puntosj1 + "\nJ2 " +  + puntosj2)
                .setCancelable(false)
                .setPositiveButton("Nuevo Juego",
                    DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    })

                .setNegativeButton("SALIR",
                    DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                        finish()
                    })

            val ad = builder.create()
            ad.show()
        }
    } // fin de la funcion finJuego

    override fun startActivity(intent: Intent?) {
        super.onDestroy()
        super.onDestroy()
        mpFondo.stop()
        mpFondo.release()
    }

}// fin de main activity