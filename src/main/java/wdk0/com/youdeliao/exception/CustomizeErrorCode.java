package wdk0.com.youdeliao.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {



    QUESTION_NOT_FOUND("你查找的问题被外星人劫走了，换个问题试试吧~");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message){
        this.message = message;
    }


}
