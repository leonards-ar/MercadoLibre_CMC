package com.ml.cmc.exception

class SecLockException extends Exception {

    Object invalidObject

    SecLockException(String message, Object invalidObject){
        super(message)
        this.invalidObject = invalidObject
    }
}

