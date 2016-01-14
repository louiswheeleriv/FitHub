package com.louiswheeleriv.fithub.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.louiswheeleriv.fithub.objects.Exercise;

import java.util.ArrayList;
import java.util.List;

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
    private static final String KEY_EXERCISE_TYPE = "exercise_type";
    private static final String KEY_EXERCISE_ID = "exercise_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NUM_REPS = "num_reps";
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
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_EXERCISE_TYPE + " TEXT" + ")";

        String CREATE_WEIGHT_EXERCISES_TABLE = "CREATE TABLE " + TABLE_WEIGHT_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EXERCISE_ID + " INTEGER NOT NULL, "
                + KEY_DATE + " DATE NOT NULL, " + KEY_NUM_REPS + " INTEGER NOT NULL, "
                + KEY_WEIGHT + " INTEGER NOT NULL" + ")";

        String CREATE_CARDIO_EXERCISES_TABLE = "CREATE TABLE " + TABLE_CARDIO_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EXERCISE_ID + " INTEGER NOT NULL, "
                + KEY_DATE + " DATE NOT NULL, " + KEY_DISTANCE + " INTEGER NOT NULL, "
                + KEY_DURATION + " INTEGER NOT NULL, " + KEY_INCLINATION + " INTEGER NOT NULL, "
                + KEY_RESISTANCE + " INTEGER NOT NULL" + ")";

        String CREATE_BODY_EXERCISES_TABLE = "CREATE TABLE " + TABLE_BODY_EXERCISES + "("
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

    public void addExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_EXERCISE_TYPE, exercise.getExerciseType());

        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    public Exercise getExercise(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXERCISES, new String[] {KEY_ID, KEY_NAME, KEY_EXERCISE_TYPE}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        int exerciseId = cursor.getInt(0);
        String name = cursor.getString(1);
        String exerciseType = cursor.getString(2);

        Exercise exercise = new Exercise(exerciseId, name, exerciseType);
        db.close();
        return exercise;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exerciseList = new ArrayList<Exercise>();

        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                exerciseList.add(exercise);
            } while(cursor.moveToNext());
        }

        db.close();
        return exerciseList;
    }

    public int getExerciseCount(){
        String countQuery = "SELECT * FROM " + TABLE_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, exercise.getName());
        values.put(KEY_EXERCISE_TYPE, exercise.getExerciseType());

        return db.update(TABLE_EXERCISES, values, KEY_ID + " = ?",
                new String[] {String.valueOf(exercise.getId())});
    }

    public int deleteExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        int numAffected = db.delete(TABLE_EXERCISES, KEY_ID + " = ?", new String[] {String.valueOf(exercise.getId())});
        db.close();
        return numAffected;
    }

    public int deleteExerciseByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor typeCursor = db.query(TABLE_EXERCISES, new String[] {KEY_ID, KEY_NAME}, KEY_NAME + " = ?",
                new String[] {name}, null, null, null, null);
        typeCursor.moveToFirst();
        int exerciseId = -1;
        try {
            exerciseId = typeCursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int numAffected = db.delete(TABLE_EXERCISES, KEY_NAME + " = ?", new String[] {name});
        db.delete(TABLE_WEIGHT_EXERCISES, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        db.delete(TABLE_CARDIO_EXERCISES, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        db.delete(TABLE_BODY_EXERCISES, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        db.delete(TABLE_GOALS, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        db.close();
        return numAffected;
    }

    public void deleteAllExercises(){
        List<Exercise> exerciseList = getAllExercises();
        for(Exercise exercise : exerciseList){
            deleteExercise(exercise);
        }
    }

}
