package com.daema.base.enums;

public enum ShellEnum {
    //쉘 타입 구분
    MATCH_DEVICE("구전산 기기 매칭작업","python3 /home/webstorm/test.py" , "/home/webstorm/test.py"),

    ;
    private String desc;
    private String command;
    private String filePath;

    ShellEnum(String desc, String command, String filePath) {
        this.desc = desc;
        this.command = command;
        this.filePath = filePath;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getCommand() {
        return command;
    }

    public String getFilePath() {
        return this.filePath;
    }

}
