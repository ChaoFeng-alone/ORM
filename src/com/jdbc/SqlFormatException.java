package com.jdbc;

class SqlFormatException extends RuntimeException {
    public SqlFormatException() {
    }

    public SqlFormatException(String message) {
        super(message);
    }
}
