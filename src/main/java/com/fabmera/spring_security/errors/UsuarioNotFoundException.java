package com.fabmera.spring_security.errors;

public class UsuarioNotFoundException extends Exception{

        public UsuarioNotFoundException(String message) {
            super("Usuario no encontrado");
        }
}
