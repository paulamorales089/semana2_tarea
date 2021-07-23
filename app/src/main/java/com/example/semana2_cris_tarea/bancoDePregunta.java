package com.example.semana2_cris_tarea;

public class bancoDePregunta {

    private int a, b, c, d, respuesta;
    private String signo, pregunta;

    private String [] operador = { "+","-","*","/"};

    public bancoDePregunta() {
        this.respuesta = 0;
        this.a = (int) (Math.random()*11);
        this.b = (int) (Math.random()*11);

        //random de las posiciones de los signos
        this.d = (int) (Math.random()*4);

        // le estoy asignanddo a signo la posicion random que va
        // a tener el operador en el arreglo, esa posicion es d
        this.signo = operador [d];

    }

    public String getPregunta() {
        if (signo.equals("/")) {
            c = a * b;
            pregunta = c + " " + signo + " " + a;
            return pregunta;
        } else {
            pregunta = a + " " + signo + " " + b;
            return pregunta;
        }
    }

    public int getRespuesta() {

        // usamos switch cuando una sola de las variables cambia de estado :)
        switch (signo){
            case "+":
                respuesta = a+b;
                break;
            case "-" :
                respuesta = a-b;
                break;
            case "*" :
                respuesta = a*b;
                break;
            case "/":
                respuesta = c/a;
                break;
        }
        return respuesta;
    }
}
