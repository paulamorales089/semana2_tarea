package com.example.semana2_cris_tarea;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // para cada variable hay que usar el nombre de la clase a la que corresponde en la vista
    private TextView contador, pregunta, puntaje;
    private Button responder, reintentar;
    private EditText respuesta;

    // presionando = despues de 10s se cambia la pregunta
    private boolean initContador, presionando;

    private int contador2, puntaje2;
    // llamando la clase bancoDePregunta
    private bancoDePregunta banco;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializar las pinches variables
        contador = findViewById(R.id.contador);
        pregunta = findViewById(R.id.pregunta);
        respuesta = findViewById(R.id.respuesta);
        responder = findViewById(R.id.responder);
        reintentar = findViewById(R.id.reintentar);
        puntaje = findViewById(R.id.puntaje);

        initContador = true;
        presionando = false;

        contador2 = 30;
        puntaje2 = 0;

        // creando la pregunta pa que se vea en la pantalla
        banco = new bancoDePregunta();
        //adjudicando a pregunta el metodo de la clase banco pregunta,
        // donde se crea la pregunta
        pregunta.setText(banco.getPregunta());


        iniciarContador();

        responder.setOnClickListener(this);
        reintentar.setOnClickListener(this);

        pregunta.setOnTouchListener(
                // ya que el metodo OnTouch requiere dos variables,
                // en el "(variable1, variable2)->" coloco dos variables
                (view, event) -> {
                    // el getAction es para obtener el evento que se esta teniendo en el momento
                    // ya sea down, up or "drag" que es move
                    switch (event.getAction()) {
                        case MotionEvent
                                .ACTION_DOWN:
                            presionando = true;
                            new Thread(
                                    () -> {
                                        for (int i = 0; i < 20; i++) {
                                            try {
                                                Thread.sleep(75);
                                                if (presionando == false) {
                                                    return;
                                                }
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        runOnUiThread(
                                                () -> {
                                                    nuevaPregunta();
                                                    respuesta.setText("");
                                                }
                                        );
                                    }
                            ).start();
                            break;
                        case MotionEvent.ACTION_UP:
                            presionando = false;
                            break;
                    }

                    return true;
                }
        );

    }

    private void iniciarContador() {
        // se pone aqui otra vez para que cada que llames el metodo empiece en 30
        contador2 = 30;

        new Thread(
                () -> {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            if (!initContador) {
                                contador2 = 30;
                                initContador = true;
                            }
                            if (contador2 > 0 && contador2 <= 30) {
                                contador2--;
                            }

                            runOnUiThread(
                                    () -> {
                                        contador.setText("00:" + contador2);
                                    }
                            );
                            if (contador2 == 0) {
                                runOnUiThread(
                                        () -> {
                                            // contador = o el boton de respoder se va a
                                            // bloquear y el boton de reintentar va a aparecer
                                            responder.setEnabled(false);
                                            // (view.VISIBLE) Funcion que hace aparecer el boton :)
                                            reintentar.setVisibility(View.VISIBLE);
                                        }
                                );
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.responder:
                respuestaM();
                break;
            case R.id.reintentar:
                reiniciar();
                break;
        }
    }


    private void reiniciar() {
        initContador = false;
        nuevaPregunta();
        puntaje2 = 0;
        puntaje.setText("Puntaje: ");
        reintentar.setVisibility(View.GONE);
        responder.setEnabled(true);
        respuesta.setText("");
    }

    private void respuestaM() {
        //creo un almacen para guardar la respuesta en "RESPUESTA USUARIO", con el getText
        // llamo al codigo de respuesta entonces con el toString lo convierto a un String
        //String respuestaUsuario = respuesta.getText().toString();

        if (respuesta.getText().toString().equals("") || respuesta.getText().toString().isEmpty()) {
            //el "makeText" es un metodo de la clase toast para generar un mensaje flotante
            // y siempre pide 3 parametros
            // el mensajo nunca se va a mostrar si no se usa el metodo ".show();"
            Toast.makeText(this, "Tienes que responder gonorrea", Toast.LENGTH_LONG).show();
        } else {
            int respuestaCorrectaOno = Integer.parseInt(respuesta.getText().toString());
            int respuestaCorrecta = banco.getRespuesta();

            if (respuestaCorrectaOno == respuestaCorrecta) {
                Toast.makeText(this, "lo hiciste chikistrikis", Toast.LENGTH_LONG).show();
                puntaje2 = puntaje2 + 5;
                puntaje.setText("Puntaje: " + puntaje2);
                nuevaPregunta();
                respuesta.setText("");
                initContador = false;
            } else {
                Toast.makeText(this, "Pero que pendejo", Toast.LENGTH_SHORT).show();
                respuesta.setText("");
            }
        }
    }

    public void nuevaPregunta() {
        banco = new bancoDePregunta();
        pregunta.setText(banco.getPregunta());
    }

}