package com.fabmera.spring_security.errors;

public class PasswordAuthenticationException extends Exception {

        public PasswordAuthenticationException(String message) {
            super("Contraseña incorrecta");
        }
}
