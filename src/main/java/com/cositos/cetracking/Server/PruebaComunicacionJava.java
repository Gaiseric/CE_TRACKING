package com.cositos.cetracking.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PruebaComunicacionJava {
    public static void main(String[] args){
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try{
            engine.eval("print('welcome to javascript execution using java')");
            engine.eval(new FileReader("pruebaComunicacion.js"));
            Invocable invocable= (Invocable)engine;
            invocable.invokeFunction("sumOfTwoNumbers", 10,20);
        }catch(ScriptException | FileNotFoundException | NoSuchMethodException e){
            e.printStackTrace();
        }
    }
    public static int sumOfTwoNumbers(int input1, int input2){
        return input1 + input2;
    }
}
