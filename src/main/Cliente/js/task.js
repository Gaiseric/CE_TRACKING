"use strict";

//console.log("Correcto!");
const texto = document.getElementById("texto");
const input = document.getElementById("enviar");
const lista = document.querySelector(".lista");

//agregar elemento
input.addEventListener("click",(e)=>{
    e.preventDefault(); //Cancelar el evento del botón
    const li = document.createElement("li"); //creando el li

    const liLista = lista.appendChild(li); //insertando el li en ul
    liLista.append(texto.value); //insertando el valor del texto

    texto.value = ""; //Limpiando el input
});