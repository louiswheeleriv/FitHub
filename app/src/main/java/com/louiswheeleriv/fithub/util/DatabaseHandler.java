package com.louiswheeleriv.fithub.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.louiswheeleriv.fithub.objects.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;

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
	 * All CRUD Operations
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
        deleteAllExerciseInstancesForExerciseId(exercise.getId());
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
        deleteAllExerciseInstancesForExerciseId(exerciseId);
        db.close();
        return numAffected;
    }

    public int deleteAllExercises(){
        List<Exercise> exerciseList = getAllExercises();
        int numAffected = 0;
        for(Exercise exercise : exerciseList){
            numAffected += deleteExercise(exercise);
        }

        return numAffected;
    }

    public int deleteAllExerciseInstancesForExerciseId(int exerciseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numAffected = 0;

        numAffected += db.delete(TABLE_WEIGHT_EXERCISES, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        numAffected += db.delete(TABLE_CARDIO_EXERCISES, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        numAffected += db.delete(TABLE_BODY_EXERCISES, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        numAffected += db.delete(TABLE_GOALS, KEY_EXERCISE_ID + " = ?", new String[] {String.valueOf(exerciseId)});
        db.close();

        return numAffected;
    }

    /*
	 * Functions for WeightExercise
	 */

    public void addWeightExercise(WeightExercise we) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE_ID, we.getExercise().getId());
        values.put(KEY_DATE, we.getDate().toString());
        values.put(KEY_NUM_REPS, we.getNumReps());
        values.put(KEY_WEIGHT, we.getWeight());

        db.insert(TABLE_WEIGHT_EXERCISES, null, values);
        db.close();
    }

    public WeightExercise getWeightExercise(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_WEIGHT_EXERCISES,
                new String[] {KEY_ID, KEY_EXERCISE_ID, KEY_DATE, KEY_NUM_REPS, KEY_WEIGHT},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        int instanceId = cursor.getInt(0);
        int exerciseId = cursor.getInt(1);
        Exercise exercise = getExercise(exerciseId);
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(cursor.getString(2));
        } catch(ParseException e) {
            Log.e("ERROR", e.getMessage());
        }
        int numReps = cursor.getInt(3);
        int weight = cursor.getInt(4);

        WeightExercise we = new WeightExercise(instanceId, exercise, date, numReps, weight);
        db.close();
        return we;
    }

    public List<WeightExercise> getWeightExercisesByExerciseId(int exerciseId) {
        Exercise exercise = null;
        List<WeightExercise> weightExerciseList = new ArrayList<WeightExercise>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_WEIGHT_EXERCISES,
                new String[] {KEY_ID, KEY_EXERCISE_ID, KEY_DATE, KEY_NUM_REPS, KEY_WEIGHT},
                KEY_EXERCISE_ID + " = ?",
                new String[] {String.valueOf(exerciseId)},
                null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int instanceId = cursor.getInt(0);
                if (exercise == null) {
                    exercise = getExercise(exerciseId);
                }
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch(ParseException e) {
                    Log.e("ERROR", e.getMessage());
                }
                int numReps = cursor.getInt(3);
                int weight = cursor.getInt(4);

                WeightExercise we = new WeightExercise(instanceId, exercise, date, numReps, weight);

                weightExerciseList.add(we);
            } while(cursor.moveToNext());
        }

        db.close();
        return weightExerciseList;
    }

    public List<WeightExercise> getAllWeightExercises() {
        Map<Integer, Exercise> exercisesById = new HashMap<Integer, Exercise>();
        List<WeightExercise> weightExerciseList = new ArrayList<WeightExercise>();

        String selectQuery = "SELECT * FROM " + TABLE_WEIGHT_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int instanceId = cursor.getInt(0);
                Integer exerciseId = cursor.getInt(1);
                Exercise exercise = (exercisesById.containsKey(exerciseId)) ? exercisesById.get(exerciseId) : getExercise(exerciseId);
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch(ParseException e) {
                    Log.e("ERROR", e.getMessage());
                }
                int numReps = cursor.getInt(3);
                int weight = cursor.getInt(4);

                WeightExercise we = new WeightExercise(instanceId, exercise, date, numReps, weight);

                weightExerciseList.add(we);
            } while(cursor.moveToNext());
        }

        db.close();
        return weightExerciseList;
    }

    public int getWeightExerciseCount(){
        String countQuery = "SELECT * FROM " + TABLE_WEIGHT_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateWeightExercise(WeightExercise we){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NUM_REPS, we.getNumReps());
        values.put(KEY_WEIGHT, we.getWeight());

        return db.update(TABLE_WEIGHT_EXERCISES, values, KEY_ID + " = ?", new String[] {String.valueOf(we.getId())});
    }

    public int deleteWeightExercise(WeightExercise we){
        SQLiteDatabase db = this.getWritableDatabase();
        int numAffected = db.delete(TABLE_WEIGHT_EXERCISES, KEY_ID + " = ?", new String[] {String.valueOf(we.getId())});
        db.close();
        return numAffected;
    }

    public int deleteAllWeightExercises(){
        List<WeightExercise> weightExerciseList = getAllWeightExercises();
        int numAffected = 0;
        for(WeightExercise we : weightExerciseList){
            numAffected += deleteWeightExercise(we);
        }

        return numAffected;
    }

    /*
	 * Functions for CardioExercise
	 */

    public void addCardioExercise(CardioExercise ce) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE_ID, ce.getExercise().getId());
        values.put(KEY_DATE, ce.getDate().toString());
        values.put(KEY_DISTANCE, ce.getDistance());
        values.put(KEY_DURATION, ce.getDuration());
        values.put(KEY_INCLINATION, ce.getInclination());
        values.put(KEY_RESISTANCE, ce.getResistance());

        db.insert(TABLE_CARDIO_EXERCISES, null, values);
        db.close();
    }

    public CardioExercise getCardioExercise(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CARDIO_EXERCISES,
                new String[] {KEY_ID, KEY_EXERCISE_ID, KEY_DATE, KEY_DISTANCE, KEY_DURATION, KEY_INCLINATION, KEY_RESISTANCE},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        int instanceId = cursor.getInt(0);
        int exerciseId = cursor.getInt(1);
        Exercise exercise = getExercise(exerciseId);
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(cursor.getString(2));
        } catch(ParseException e) {
            Log.e("ERROR", e.getMessage());
        }
        int distance = cursor.getInt(3);
        int duration = cursor.getInt(4);
        int inclination = cursor.getInt(5);
        int resistance = cursor.getInt(6);

        CardioExercise ce = new CardioExercise(instanceId, exercise, date, distance, duration, inclination, resistance);
        db.close();
        return ce;
    }

    public List<CardioExercise> getCardioExercisesByExerciseId(int exerciseId) {
        Exercise exercise = null;
        List<CardioExercise> cardioExerciseList = new ArrayList<CardioExercise>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_CARDIO_EXERCISES,
                new String[] {KEY_ID, KEY_EXERCISE_ID, KEY_DATE, KEY_DISTANCE, KEY_DURATION, KEY_INCLINATION, KEY_RESISTANCE},
                KEY_EXERCISE_ID + " = ?",
                new String[] {String.valueOf(exerciseId)},
                null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int instanceId = cursor.getInt(0);
                if (exercise == null) {
                    exercise = getExercise(exerciseId);
                }
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch(ParseException e) {
                    Log.e("ERROR", e.getMessage());
                }
                int distance = cursor.getInt(3);
                int duration = cursor.getInt(4);
                int inclination = cursor.getInt(5);
                int resistance = cursor.getInt(6);

                CardioExercise ce = new CardioExercise(instanceId, exercise, date, distance, duration, inclination, resistance);

                cardioExerciseList.add(ce);
            } while(cursor.moveToNext());
        }

        db.close();
        return cardioExerciseList;
    }

    public List<CardioExercise> getAllCardioExercises() {
        Map<Integer, Exercise> exercisesById = new HashMap<Integer, Exercise>();
        List<CardioExercise> cardioExerciseList = new ArrayList<CardioExercise>();

        String selectQuery = "SELECT * FROM " + TABLE_CARDIO_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int instanceId = cursor.getInt(0);
                Integer exerciseId = cursor.getInt(1);
                Exercise exercise = (exercisesById.containsKey(exerciseId)) ? exercisesById.get(exerciseId) : getExercise(exerciseId);
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch(ParseException e) {
                    Log.e("ERROR", e.getMessage());
                }
                int distance = cursor.getInt(3);
                int duration = cursor.getInt(4);
                int inclination = cursor.getInt(5);
                int resistance = cursor.getInt(6);

                CardioExercise ce = new CardioExercise(instanceId, exercise, date, distance, duration, inclination, resistance);

                cardioExerciseList.add(ce);
            } while(cursor.moveToNext());
        }

        db.close();
        return cardioExerciseList;
    }

    public int getCardioExerciseCount(){
        String countQuery = "SELECT * FROM " + TABLE_CARDIO_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateCardioExercise(CardioExercise ce){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DISTANCE, ce.getDistance());
        values.put(KEY_DURATION, ce.getDuration());
        values.put(KEY_INCLINATION, ce.getInclination());
        values.put(KEY_RESISTANCE, ce.getResistance());

        return db.update(TABLE_CARDIO_EXERCISES, values, KEY_ID + " = ?", new String[] {String.valueOf(ce.getId())});
    }

    public int deleteCardioExercise(CardioExercise ce){
        SQLiteDatabase db = this.getWritableDatabase();
        int numAffected = db.delete(TABLE_CARDIO_EXERCISES, KEY_ID + " = ?", new String[] {String.valueOf(ce.getId())});
        db.close();
        return numAffected;
    }

    public int deleteAllCardioExercises(){
        List<CardioExercise> cardioExerciseList = getAllCardioExercises();
        int numAffected = 0;
        for(CardioExercise ce : cardioExerciseList){
            numAffected += deleteCardioExercise(ce);
        }

        return numAffected;
    }

    /*
	 * Functions for BodyExercise
	 */

    public void addBodyExercise(BodyExercise be) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXERCISE_ID, be.getExercise().getId());
        values.put(KEY_DATE, be.getDate().toString());
        values.put(KEY_NUM_REPS, be.getNumReps());
        values.put(KEY_DURATION, be.getDuration());

        db.insert(TABLE_BODY_EXERCISES, null, values);
        db.close();
    }

    public BodyExercise getBodyExercise(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_BODY_EXERCISES,
                new String[] {KEY_ID, KEY_EXERCISE_ID, KEY_DATE, KEY_NUM_REPS, KEY_DURATION},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        int instanceId = cursor.getInt(0);
        int exerciseId = cursor.getInt(1);
        Exercise exercise = getExercise(exerciseId);
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(cursor.getString(2));
        } catch(ParseException e) {
            Log.e("ERROR", e.getMessage());
        }
        int numReps = cursor.getInt(3);
        int duration = cursor.getInt(4);

        BodyExercise be = new BodyExercise(instanceId, exercise, date, numReps, duration);
        db.close();
        return be;
    }

    public List<BodyExercise> getBodyExercisesByExerciseId(int exerciseId) {
        Exercise exercise = null;
        List<BodyExercise> bodyExerciseList = new ArrayList<BodyExercise>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_BODY_EXERCISES,
                new String[] {KEY_ID, KEY_EXERCISE_ID, KEY_DATE, KEY_NUM_REPS, KEY_DURATION},
                KEY_EXERCISE_ID + " = ?",
                new String[] {String.valueOf(exerciseId)},
                null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int instanceId = cursor.getInt(0);
                if (exercise == null) {
                    exercise = getExercise(exerciseId);
                }
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch(ParseException e) {
                    Log.e("ERROR", e.getMessage());
                }
                int numReps = cursor.getInt(3);
                int duration = cursor.getInt(4);

                BodyExercise be = new BodyExercise(instanceId, exercise, date, numReps, duration);

                bodyExerciseList.add(be);
            } while(cursor.moveToNext());
        }

        db.close();
        return bodyExerciseList;
    }

    public List<BodyExercise> getAllBodyExercises() {
        Map<Integer, Exercise> exercisesById = new HashMap<Integer, Exercise>();
        List<BodyExercise> bodyExerciseList = new ArrayList<BodyExercise>();

        String selectQuery = "SELECT * FROM " + TABLE_BODY_EXERCISES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int instanceId = cursor.getInt(0);
                Integer exerciseId = cursor.getInt(1);
                Exercise exercise = (exercisesById.containsKey(exerciseId)) ? exercisesById.get(exerciseId) : getExercise(exerciseId);
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch(ParseException e) {
                    Log.e("ERROR", e.getMessage());
                }
                int numReps = cursor.getInt(3);
                int duration = cursor.getInt(4);

                BodyExercise be = new BodyExercise(instanceId, exercise, date, numReps, duration);

                bodyExerciseList.add(be);
            } while(cursor.moveToNext());
        }

        db.close();
        return bodyExerciseList;
    }

    public int getBodyExerciseCount(){
        String countQuery = "SELECT * FROM " + TABLE_BODY_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateBodyExercise(BodyExercise be){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NUM_REPS, be.getNumReps());
        values.put(KEY_DURATION, be.getDuration());

        return db.update(TABLE_BODY_EXERCISES, values, KEY_ID + " = ?", new String[] {String.valueOf(be.getId())});
    }

    public int deleteBodyExercise(BodyExercise be){
        SQLiteDatabase db = this.getWritableDatabase();
        int numAffected = db.delete(TABLE_BODY_EXERCISES, KEY_ID + " = ?", new String[] {String.valueOf(be.getId())});
        db.close();
        return numAffected;
    }

    public int deleteAllBodyExercises(){
        List<BodyExercise> bodyExerciseList = getAllBodyExercises();
        int numAffected = 0;
        for(BodyExercise be : bodyExerciseList){
            numAffected += deleteBodyExercise(be);
        }

        return numAffected;
    }

}
