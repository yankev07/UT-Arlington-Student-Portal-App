package com.segroup9.uta_stud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.segroup9.uta_stud.Helper.ContactInformation;

import java.util.ArrayList;

/**
 * Created by kevinyanogo on 9/29/18.
 */

public class DatabaseSQL extends SQLiteOpenHelper {

    private static final String DATABASE = "UTA_STUD_DB";

    private static final String USERINFO = "userinfotable";
    private static final String RECEIVER = "chatreceivertable";
    private static final String CONTACTS = "contactstable";
    private static final String PROFESSORS = "professorstable";
    private static final String EVENTS = "eventstable";

    // Attributes of the userinfotable
    private static final String UID = "uid";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String IDNUMBER = "studentid";
    private static final String EMAIL = "email";
    private static final String COURSE1 = "course1";
    private static final String COURSE2 = "course2";
    private static final String COURSE3 = "course3";
    private static final String PROFESSOR1 = "professor1";
    private static final String PROFESSOR2 = "professor2";
    private static final String PROFESSOR3 = "professor3";
    private static final String STATUS = "status";

    // Attributes of the userinfotable
    private static final String EVENTNAME = "eventname";
    private static final String EVENTDATE = "eventdate";
    private static final String STARTTIME = "eventstart";
    private static final String ENDTIME = "eventend";

    // Attributes of the chatreceivertable
    private static final String RECEIVER_UID = "receiver_uid";
    private static final String RECEIVER_EMAIL = "receiver_email";
    private static final String RECEIVER_TOKEN = "receiver_token";

    // Attributes of the professorstable
    /*private static final String PROFESSOR_NAME = "professor_name";
    private static final String ASSISTANT_NAME = "assistant_name";
    private static final String PROFESSOR_OFFICE = "professor_office";
    private static final String ASSISTANT_OFFICE = "assistant_office";
    private static final String PROFESSOR_EMAIL = "professor_email";
    private static final String ASSISTANT_EMAIL = "assistant_email";
    private static final String PROFESSOR_PHONE = "professor_phone";
    private static final String ASSISTANT_PHONE = "assistant_phone";
    private static final String PROFESSOR_HOURS = "professor_phone";
    private static final String ASSISTANT_HOURS = "assistant_phone";*/


    public DatabaseSQL(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS userinfotable" + " (" + UID + " TEXT, " + FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " + IDNUMBER + " TEXT, " + EMAIL + " TEXT, " + COURSE1 + " TEXT, " + COURSE2 + " TEXT, " + COURSE3 + " TEXT, " + PROFESSOR1 + " TEXT, " + PROFESSOR2 + " TEXT, " + PROFESSOR3 + " TEXT, " + STATUS + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS contactstable" + " (" + UID + " TEXT, " + FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " + EMAIL + " TEXT, " + COURSE1 + " TEXT, " + COURSE2 + " TEXT, " + COURSE3 + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS chatreceivertable" + " (" + RECEIVER_UID + " TEXT, " + RECEIVER_EMAIL + " TEXT, " + RECEIVER_TOKEN + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS eventstable" + " (" + EVENTNAME + " TEXT, " + EVENTDATE + " TEXT, " + STARTTIME + " TEXT, " + ENDTIME + " TEXT)");
        //db.execSQL("CREATE TABLE IF NOT EXISTS eventstable" + " (" + PROFESSOR_NAME + " TEXT, " + ASSISTANT_NAME + " TEXT, " + PROFESSOR_OFFICE + " TEXT, " + ASSISTANT_OFFICE + " TEXT, " + PROFESSOR_EMAIL + " TEXT, " + ASSISTANT_EMAIL + " TEXT, " + PROFESSOR_PHONE + " TEXT, " + ASSISTANT_PHONE + " TEXT, " + PROFESSOR_HOURS + " TEXT, " + ASSISTANT_HOURS + " TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECEIVER);
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS);
        //db.execSQL("DROP TABLE IF EXISTS " + PROFESSORS);
        onCreate(db);
    }

    public void emptyInfoTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ USERINFO);
        db.close();
    }


    public void emptyReceiverTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ RECEIVER);
        db.close();
    }


    public void saveUserInfo(String uid, String firstName, String lastName, String studentID, String email, String course1, String course2, String course3, String professor1, String professor2, String professor3, String status){
        emptyInfoTable();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UID,uid);
        values.put(FIRSTNAME,firstName);
        values.put(LASTNAME,lastName);
        values.put(IDNUMBER,studentID);
        values.put(EMAIL,email);
        values.put(COURSE1,course1);
        values.put(COURSE2,course2);
        values.put(COURSE3,course3);
        values.put(PROFESSOR1,professor1);
        values.put(PROFESSOR2,professor2);
        values.put(PROFESSOR3,professor3);
        values.put(STATUS,status);
        db.insert(USERINFO, null, values);
    }

    public void saveEventInfo(String eventName, String eventDate, String startTime, String endTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENTNAME,eventName);
        values.put(EVENTDATE,eventDate);
        values.put(STARTTIME,startTime);
        values.put(ENDTIME,endTime);
        db.insert(EVENTS, null, values);
    }


    public void saveReceiverInfo(String uid, String email, String token){
        emptyReceiverTable();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECEIVER_UID,uid);
        values.put(RECEIVER_EMAIL,email);
        values.put(RECEIVER_TOKEN,token);
        db.insert(RECEIVER, null, values);
    }

    public void saveNewContact(String uid, String firstName, String lastName, String email, String course1, String course2, String course3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UID,uid);
        values.put(FIRSTNAME,firstName);
        values.put(LASTNAME,lastName);
        values.put(EMAIL,email);
        values.put(COURSE1,course1);
        values.put(COURSE2,course2);
        values.put(COURSE3,course3);
        db.insert(CONTACTS, null, values);
    }


    public ArrayList<String> fetchUserInfo() {
        ArrayList<String> myArray= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            myArray.add(cur.getString(cur.getColumnIndex(UID)));
            myArray.add(cur.getString(cur.getColumnIndex(FIRSTNAME)));
            myArray.add(cur.getString(cur.getColumnIndex(LASTNAME)));
            myArray.add(cur.getString(cur.getColumnIndex(IDNUMBER)));
            myArray.add(cur.getString(cur.getColumnIndex(EMAIL)));
            myArray.add(cur.getString(cur.getColumnIndex(COURSE1)));
            myArray.add(cur.getString(cur.getColumnIndex(COURSE2)));
            myArray.add(cur.getString(cur.getColumnIndex(COURSE3)));
            cur.moveToNext();
        }
        return myArray;
    }


    public Boolean adminPrivilleges() {
        String status = "User";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            status = cur.getString(cur.getColumnIndex(STATUS));
            cur.moveToNext();
        }

        if(status.equals("Admin")){
            return true;
        }
        else
            return false;
    }

    public ArrayList<ContactInformation> fetchContactsList() {
        ArrayList<ContactInformation> myArray= new ArrayList<ContactInformation>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ CONTACTS, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            String uid = cur.getString(cur.getColumnIndex(UID));
            String firstName = cur.getString(cur.getColumnIndex(FIRSTNAME));
            String lastName = cur.getString(cur.getColumnIndex(LASTNAME));
            String email = cur.getString(cur.getColumnIndex(EMAIL));
            String course1 = cur.getString(cur.getColumnIndex(COURSE1));
            String course2 = cur.getString(cur.getColumnIndex(COURSE2));
            String course3 = cur.getString(cur.getColumnIndex(COURSE3));
            myArray.add(new ContactInformation(uid, firstName, lastName, email, course1, course2, course3));
            cur.moveToNext();
        }
        return myArray;
    }

    public ArrayList<String> displayEnrolledCourses() {
        ArrayList<String> myArray= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            myArray.add(cur.getString(cur.getColumnIndex(COURSE1)));
            myArray.add(cur.getString(cur.getColumnIndex(COURSE2)));
            myArray.add(cur.getString(cur.getColumnIndex(COURSE3)));
            cur.moveToNext();
        }
        return myArray;
    }

    public ArrayList<String> displayEvents() {
        ArrayList<String> myArray= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ EVENTS, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            myArray.add(cur.getString(cur.getColumnIndex(EVENTNAME)));
            cur.moveToNext();
        }
        return myArray;
    }

    public void deleteAnnouncement(String eventName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENTS, EVENTNAME + " = ?" , new String[]{eventName});
    }

    public void deleteContact(String contactEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS, EMAIL + " = ?" , new String[]{contactEmail});
    }

    public ArrayList<String> displayProfessorsNames() {
        ArrayList<String> myArray= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            myArray.add(cur.getString(cur.getColumnIndex(PROFESSOR1)));
            myArray.add(cur.getString(cur.getColumnIndex(PROFESSOR2)));
            myArray.add(cur.getString(cur.getColumnIndex(PROFESSOR3)));
            cur.moveToNext();
        }
        return myArray;
    }

    public String getProfessorName(String courseName){

        String professorName = "-";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            if(cur.getString(cur.getColumnIndex(COURSE1)).equals(courseName))
                professorName = cur.getString(cur.getColumnIndex(PROFESSOR1));
            else if(cur.getString(cur.getColumnIndex(COURSE2)).equals(courseName))
                professorName = cur.getString(cur.getColumnIndex(PROFESSOR2));
            else
                professorName = cur.getString(cur.getColumnIndex(PROFESSOR3));
            cur.moveToNext();
        }

        return professorName;
    }


    public String getEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        String email = cur.getString(cur.getColumnIndex(EMAIL));
        return email;
    }

    public String getUid(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        String uid = cur.getString(cur.getColumnIndex(UID));
        return uid;
    }

    public String getReceiverEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ RECEIVER, null);
        cur.moveToFirst();
        String email = cur.getString(cur.getColumnIndex(RECEIVER_EMAIL));
        return email;
    }

    public String getReceiverUid(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ RECEIVER, null);
        cur.moveToFirst();
        String uid = cur.getString(cur.getColumnIndex(RECEIVER_UID));
        return uid;
    }

    public String getFirebaseToken(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ RECEIVER, null);
        cur.moveToFirst();
        String token = cur.getString(cur.getColumnIndex(RECEIVER_TOKEN));
        return token;
    }

}
