package com.louiswheeleriv.fithub;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fitHubDB";

    private static final String TABLE_EXERCISES = "exercises";
    private static final String TABLE_WEIGHT_EXERCISES = "weight_exercises";
    private static final String TABLE_CARDIO_EXERCISES = "cardio_exercises";
    private static final String TABLE_BODY_EXERCISES = "body_exercises";
    private static final String TABLE_GOALS = "goals";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MUSCLES_USED = "muscles_used";
    private static final String KEY_EXERCISE_TYPE = "exercise_type";
    private static final String KEY_EXERCISE_ID = "exercise_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NUM_REPS = "num_reps";
    private static final String KEY_NUM_SETS = "num_sets";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_INCLINATION = "inclination";
    private static final String KEY_RESISTANCE = "resistance";
    private static final String KEY_TARGET_REPS = "target_reps";
    private static final String KEY_TARGET_WEIGHT = "target_weight";
    private static final String KEY_TARGET_DISTANCE = "target_distance";
    private static final String KEY_TARGET_DURATION = "target_duration";

    public DatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_MUSCLES_USED + " TEXT, " + KEY_EXERCISE_TYPE + " TEXT" + ")";

        String CREATE_WEIGHT_EXERCISES_TABLE = "CREATE TABLE " + TABLE_WEIGHT_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EXERCISE_ID + " INTEGER NOT NULL, "
                + KEY_DATE + " DATE NOT NULL, " + KEY_NUM_REPS + " INTEGER NOT NULL, "
                + KEY_NUM_SETS + " INTEGER NOT NULL, " + KEY_WEIGHT + " INTEGER NOT NULL" + ")";

        String CREATE_CARDIO_EXERCISES_TABLE = "CREATE TABLE " + TABLE_WEIGHT_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EXERCISE_ID + " INTEGER NOT NULL, "
                + KEY_DATE + " DATE NOT NULL, " + KEY_DISTANCE + " INTEGER NOT NULL, "
                + KEY_DURATION + " INTEGER NOT NULL, " + KEY_INCLINATION + " INTEGER NOT NULL, "
                + KEY_RESISTANCE + " INTEGER NOT NULL" + ")";

        String CREATE_BODY_EXERCISES_TABLE = "CREATE TABLE " + TABLE_WEIGHT_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EXERCISE_ID + " INTEGER NOT NULL, "
                + KEY_DATE + " DATE NOT NULL, " + KEY_NUM_REPS + " INTEGER NOT NULL, "
                + KEY_DURATION + " INTEGER NOT NULL" + ")";

        String CREATE_GOALS_TABLE = "CREATE TABLE " + TABLE_GOALS + "("
                + KEY_EXERCISE_ID + " INTEGER PRIMARY KEY," + KEY_TARGET_REPS + " INTEGER, "
                + KEY_TARGET_WEIGHT + " INTEGER, " + KEY_TARGET_DISTANCE + " INTEGER, "
                + KEY_TARGET_DURATION + " INTEGER" + ")";

        db.execSQL(CREATE_EXERCISES_TABLE);
        db.execSQL(CREATE_WEIGHT_EXERCISES_TABLE);
        db.execSQL(CREATE_CARDIO_EXERCISES_TABLE);
        db.execSQL(CREATE_BODY_EXERCISES_TABLE);
        db.execSQL(CREATE_GOALS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDIO_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BODY_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        onCreate(db);
    }

	/*
	 * All CRUD Operations (Create, Read, Update, Delete)
	 */

    /*
	 * Functions for Exercise
	 */

    /*

    public void addExercise(HabitType habitType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, habitType.getName());
        values.put(KEY_GOODHABIT, habitType.isGoodHabit());

        db.insert(TABLE_HABITTYPES, null, values);
        db.close();
    }

    public HabitType getHabitType(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HABITTYPES, new String[] {KEY_ID, KEY_NAME, KEY_GOODHABIT}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        int habitTypeId = cursor.getInt(0);
        String name = cursor.getString(1);
        boolean goodHabit = true;
        if(cursor.getInt(2) == 0){
            goodHabit = false;
        }

        HabitType habitType = new HabitType(habitTypeId, name, goodHabit);
        db.close();
        return habitType;
    }

    public List<HabitType> getAllHabitTypes(){
        List<HabitType> habitTypeList = new ArrayList<HabitType>();

        String selectQuery = "SELECT * FROM " + TABLE_HABITTYPES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                HabitType habitType = new HabitType();
                habitType.setId(Integer.parseInt(cursor.getString(0)));
                habitType.setName(cursor.getString(1));
                habitTypeList.add(habitType);
            }while(cursor.moveToNext());
        }

        db.close();
        return habitTypeList;
    }

    public int getHabitTypesCount(){
        String countQuery = "SELECT * FROM " + TABLE_HABITTYPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateHabitType(HabitType habitType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, habitType.getName());

        return db.update(TABLE_HABITTYPES, values, KEY_ID + " = ?",
                new String[] {String.valueOf(habitType.getId())});
    }

    public int deleteHabitType(HabitType habitType){
        SQLiteDatabase db = this.getWritableDatabase();
        int numAffected = db.delete(TABLE_HABITTYPES, KEY_ID + " = ?", new String[] {String.valueOf(habitType.getId())});
        db.close();
        return numAffected;
    }

    public int deleteHabitTypeByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor typeCursor = db.query(TABLE_HABITTYPES, new String[] {KEY_ID, KEY_NAME}, KEY_NAME + " = ?",
                new String[] {name}, null, null, null, null);
        typeCursor.moveToFirst();
        int habitTypeId = -1;
        try {
            habitTypeId = typeCursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int numAffected = db.delete(TABLE_HABITTYPES, KEY_NAME + " = ?", new String[] {name});
        db.delete(TABLE_HABITS, KEY_HABITTYPE_ID + " = ?", new String[] {String.valueOf(habitTypeId)});
        db.delete(TABLE_GOALS, KEY_HABITTYPE_ID + " = ?", new String[] {String.valueOf(habitTypeId)});
        db.close();
        return numAffected;
    }

    public void deleteAllHabitTypes(){
        List<HabitType> habitTypeList = getAllHabitTypes();
        for(HabitType ht : habitTypeList){
            deleteHabitType(ht);
        }
    }

    */

}
