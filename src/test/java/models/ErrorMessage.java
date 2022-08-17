package models;


import lombok.Getter;

@Getter
public class ErrorMessage {
    private String timestamp;
    private String error;
    private String path;
    private String message;
    private long status;
}
