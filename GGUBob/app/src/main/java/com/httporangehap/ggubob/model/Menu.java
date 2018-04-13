package com.httporangehap.ggubob.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.httporangehap.ggubob.R;

/**
 * 메뉴 데이터를 저장할 모델 객체.
 */
public class Menu {

    private static final String TAG = "Menu";

    /**
     * 식사 종류.
     **/
    public enum MealType {
        //color 지정.
        BREAKFAST(R.string.meal_breakfast, R.color.color_breakfast), // 아침
        LUNCH(R.string.meal_lunch, R.color.color_lunch), // 점심
        DINNER(R.string.meal_dinner, R.color.color_dinner); // 저녁

        public final int textId;
        public final int bgColorId;

        MealType(int textId, int bgColorId) {
            this.textId = textId;
            this.bgColorId = bgColorId;
        }
    }

    /**
     * 식사 날짜.
     */
    private final String dateString;
    private final int month;
    private final int day;

    /**
     * 식사 종류.
     */
    private final MealType mealType;

    /**
     * 반찬.
     */
    private final String dishes;

    /**
     * 현재 메뉴인지 여부.
     */
    private boolean isNow;

    /**
     * 메뉴 객체를 생성합니다.
     * 생성자에서 모든 데이터를 저장하고 Set함수를 만들지 않은 것은
     * 한번 생성하면 이후에는 변경을 허용하지 않는다는 의미입니다.
     *
     *
     * @param dateString 날짜 문자열. ("11/5 [토]")
     * @param mealType   식사 종류. (조식, 중식, 석식)
     * @param dishes     반찬 (한칸씩 띄어서 입력됨. "밥 된장국 김치 콩나물 ...") -> 추가 공지도 반찬 메뉴에 포함.
     */
    public Menu(String dateString, MealType mealType, String dishes) {
        this.dateString = dateString;
        this.mealType = mealType;
        this.dishes = dishes;
        // 정규표현식 참고: https://ko.wikipedia.org/wiki/정규_표현식
        String[] dates = dateString.split("[/ ]"); // '/' 나 ' '로 문자열을 나눈다.
        if (dates.length > 2) {
            month = Integer.valueOf(dates[0]);
            day = Integer.valueOf(dates[1]);
        } else {
            // Error.
            month = 0;
            day = 0;
        }
    }

    /**
     * 날짜 문자열을 반환한다.
     */
    public String getDateString() {
        return dateString;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /**
     * 식사 종류를 반환한다.
     */
    public MealType getMealType() {
        return mealType;
    }

    /**
     * 반찬 문자열을 반환한다.
     *
     * @return 한칸씩 띄어써진 형태로 반환한다. ("밥 된장국 김치 콩나물 ...") -> 추가 공지도 반찬 메뉴에 포함.
     */
    public String getDishes() {
        return dishes;
    }

    /**
     * 반찬 문자열의 배열을 반환한다.
     *
     * @return 반찬들을 포함하는 문자열 배열.
     */
    public String[] getDishArray() {
        // " " 공백 문자열로 String을 잘라낸다.
        return dishes.split(" ");
    }

    public void setNow(int month, int day, MealType mealType) {
        isNow = (this.month == month && this.day == day && this.mealType == mealType);
    } //MainAdapter에서의 20시 이후 문제.

    /**
     * 만약 현재 시간에 해당하는 메뉴인지 판단한다.
     *
     * @return 현재 시간에 해당한다면 true, 아니면 false.
     */
    public boolean isNow() {
        return isNow;
    }
}
