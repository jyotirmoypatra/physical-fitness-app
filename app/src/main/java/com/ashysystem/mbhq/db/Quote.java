package com.ashysystem.mbhq.db;

public class Quote {
    public static final String TABLE_NAME = "Quotes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUOTE = "quote";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_EDITED = "isEdited";
    public static final String COLUMN_FAV = "isFav";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MONTH = "month";

    private int id;
    private String quote;
    private String date;



    private String month;
    private String author;
    private Integer isEdited,isFav;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    ///+ COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_QUOTE + " TEXT,"
                    + COLUMN_AUTHOR + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_MONTH + " TEXT,"
                    + COLUMN_EDITED + " INTEGER,"
                    + COLUMN_FAV + " INTEGER"
                    + ")";



    public Quote(int id, String quote, String author,Integer isEdited,Integer isFav,String date,String month) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.isEdited=isEdited;
        this.isFav=isFav;
        this.date=date;
        this.month=month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Integer isEdited) {
        this.isEdited = isEdited;
    }

    public Integer getIsFav() {
        return isFav;
    }

    public void setIsFav(Integer isFav) {
        this.isFav = isFav;
    }
}
