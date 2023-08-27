package br.com.pedrocamargo.esync.enums;

public enum UFEnum {
    RO("RO"),
    AC("AC"),
    AM("AM"),
    RR("RR"),
    PA("PA"),
    AP("AP"),
    TO("TO"),
    MA("MA"),
    PI("PI"),
    CE("CE"),
    RN("RN"),
    PB("PB"),
    PE("PE"),
    AL("AL"),
    SE("SE"),
    BA("BA"),
    MG("MG"),
    ES("ES"),
    RJ("RJ"),
    SP("SP"),
    PR("PR"),
    SC("SC"),
    RS("RS"),
    MS("MS"),
    MT("MT"),
    GO("GO"),
    DF("DF");

    private String description;

    private UFEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static UFEnum getEnum(String enumString){
        UFEnum[] enums = UFEnum.values();

        for (UFEnum item : enums) {
            if(item.description == enumString){
                return item;
            }
        }

        throw new IllegalArgumentException("UF informado é inválida.");
    }
}
