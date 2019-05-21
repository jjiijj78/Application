package b1g4.com.yourseat.bus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * CongestinoClass 혼잡도 정보를 계산하고 해당 정보를 StationClass에 저장한다.
 */
public class CongestionClass {
    // 정류장을 순번대로 저장.
    public ArrayList<String> stationList = new ArrayList<String>();

    // "중앙대후문___동작01"형식으로 저장, 구분자:"___"
    public String routeName;
    /**
     * 한달중에 평일이 며칠이 있는지, 토요일이 며칠이 있는지, 일요일이 며칠이 있는지 세어서 저장 0:평일날짜수, 1:토요일날짜수,
     * 2:일요일날짜수
     */
    private HashMap<String, int[]> totalDays = new HashMap<String, int[]>();

    /**
     * 한달중에 평일|토요일|일요일에 사람이 총 몇명이 타고 몇명이 하차했는지 저장 [0][0] 평일총승차 [0][1] 평일총하차 [1][0]
     * 토요일총승차 [1][1] 토요일총하차 [2][0] 일요일총승차 [2][1] 일요일총하차
     */
    private HashMap<String, Double[][]> totalPeople = new HashMap<String, Double[][]>();

    /**
     * 특정시각에 사람이 총 몇명이 타고 몇명이 하차했는지 저장 [0][0] 0-1시 총승차 [0][1] 1-2시 총승차 [0][2] 2-3시
     * 총승차 [0][3] 3-4시 총승차 ....... [1][0] 0-1시 총하차 [1][1] 1-2시 총하차 [1][2] 2-3시 총하차
     * [1][3] 3-4시 총하차 .......
     */
    private HashMap<String, Double[][]> totalByTime = new HashMap<String, Double[][]>();

    /**
     * 평일0-23시, 토요일 0-23시, 일요일 0-23시 순으로 저장 Key : StationID Value : Congestion info,
     */
    public HashMap<String, HashMap<Integer, int[]>> congestion = new HashMap<String, HashMap<Integer, int[]>>();


    /**
     * key : 정류장ID
     * value : 요일별/시간대별 승.하차 인원
     */
    public HashMap<String, HashMap<Integer, Double[][]>> dayPassengerNum_getOnOff = new HashMap<String, HashMap<Integer, Double[][]>>();

    /**
     * key : 정류장ID
     * value : 요일별/시간대별 재차인원
     * 재차인원 정보는 Route별로 관리되어야 함.
     */
    public HashMap<String, HashMap<Integer, Double[]>> passengerNum = new HashMap<String, HashMap<Integer, Double[]>>();

    /**
     * default constructor
     */
    public CongestionClass(String routeName) {
        this.initCongestion();
    }

    /**
     * congestion변수 초기화
     */
    private void initCongestion() {

    }

    public void setStationList(ArrayList<String> list){
        this.stationList = list;
    }

    /**
     * 특정 시각에 승차,하차인원이 모두 없으면 false, 하나라도 있으면 true
     */
    public boolean IsPassengerExist(String stationId, int hour) {
        // 승차
        if (totalByTime.get(stationId)[0][hour] == 0 && totalByTime.get(stationId)[1][hour] == 0)
            return false;
        return true;
    }

    /**
     * stationID_routeName에서 routeName를 반환
     */
    public String getrouteName() {
        return this.routeName;
    }

    /**
     * 원하는 시간대를 쪼개 임의로 hh시 mm분에 대한 혼잡도 계산 수식 검사 후 수정 여부 판단.
     *
     * @param day    : 평일, 토요일, 일요일 중 입력
     * @param hour   : 0~23시
     * @param minute : 0~59분
     * @return hh시 mm분일 때 버스의 혼잡도
     */
    public double getCongestion(int day, int hour, int minute, String stationId) {
        return ((this.congestion.get(stationId).get(day)[hour+1] - this.congestion.get(stationId).get(day)[hour]) * (minute / 60)
                + this.congestion.get(stationId).get(day)[hour]);
    }

    /**
     * 재차인원 계산 승차-하차, 시간대별로 나와야함.
     */
    public Double calcPassengerNum(int day, int hour, int minute, String stationId) {

        return ((this.passengerNum.get(stationId).get(day)[(hour+1)%24]-this.passengerNum.get(stationId).get(day)[(hour)%24])* (minute / 60)
                +this.passengerNum.get(stationId).get(day)[hour]);
    }


    /**
     *
     * @param day
     */
    public void setTotalDaysInfo(String stationId, int day) {
        if (!this.totalDays.containsKey(stationId)) {
            int tmp[] = new int[3];
            for (int i = 0; i < 3; i++) {
                tmp[i] = 0;
            }
            this.totalDays.put(stationId, tmp);
        }
        this.totalDays.get(stationId)[day] += 1;
    }

    /**
     *
     * @param day
     * @param ride
     * @param alight
     */
    public void setTotalPeopleInfo(String stationId, int day, Double ride, Double alight) {

        if (!this.totalPeople.containsKey(stationId)) {
            Double tmp[][] = new Double[3][2];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    tmp[i][j] = 0.0;
                }
            }
            this.totalPeople.put(stationId, tmp);
        }
        this.totalPeople.get(stationId)[day][0] += ride;
        this.totalPeople.get(stationId)[day][1] += alight;
    }

    public void setTotalByTimeInfo(String stationId, int hour, Double ride, Double alight) {
        if (!this.totalByTime.containsKey(stationId)) {
            Double tmp[][] = new Double[2][24];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 24; j++) {
                    tmp[i][j] = 0.0;
                }
            }
            this.totalByTime.put(stationId, tmp);
        }
        this.totalByTime.get(stationId)[0][hour] += ride;
        this.totalByTime.get(stationId)[1][hour] += alight;
    }

    /**
     * 해당날짜의 요일을 판별 (평|토|일)
     *
     * @param date yyyyMMdd형식의 날짜 String
     * @return 0(평일) 1(토요일) 2(일요일)
     * @TODO 공휴일을 판별할수 있는 코드를 여기에 삽입
     */
    public int WhatDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nDate;
        try {
            nDate = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(nDate);
            int dayNum = cal.get(Calendar.DAY_OF_WEEK);
            if (dayNum == 2 | dayNum == 3 | dayNum == 4 | dayNum == 5 | dayNum == 6)
                return 0;// 평일
            else if (dayNum == 7)
                return 1;// 토요일
            else if (dayNum == 1)
                return 2; // 일요일
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 평,토,일 별 시간당 승차,하차인원수를 구한다.
     *
     * @param day 0평일, 1토요일, 2일요일
     */
    public void calcGettingOnAndOff() {
        Double[][] GettingOnAndOff = new Double[2][24];
        // 승하차 인원 계산
        for (String key : this.totalByTime.keySet()) {
            HashMap<Integer, Double[][]> tmp = new HashMap<Integer, Double[][]>();
            for(int day =0; day<3; day++){
                for (int time = 0; time < 24; time++) { // 0-23시
                    // 승차
                    GettingOnAndOff[0][time] = totalByTime.get(key)[0][time]
                            * (totalPeople.get(key)[day][0] / totalDays.get(key)[day])
                            / (totalPeople.get(key)[0][0] + totalPeople.get(key)[1][0] + totalPeople.get(key)[2][0]);
                    // 하차
                    GettingOnAndOff[1][time] = totalByTime.get(key)[1][time]
                            * (totalPeople.get(key)[day][1] / totalDays.get(key)[day])
                            / (totalPeople.get(key)[0][1] + totalPeople.get(key)[1][1] + totalPeople.get(key)[2][1]);
                }
                tmp.put(day, GettingOnAndOff);
            }
            this.dayPassengerNum_getOnOff.put(key, tmp);
        }
    }

    public boolean isStationExist(String stationId) {
        return this.totalDays.containsKey(stationId);
    }

}
