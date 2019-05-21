package b1g4.com.yourseat;

public class CurrentLocationXY {
    private static CurrentLocationXY currentLocationXYInstance = new CurrentLocationXY();

    private String x;
    private String y;

    private CurrentLocationXY(){}

    public static CurrentLocationXY getInstance() {
        if(currentLocationXYInstance == null) {
            currentLocationXYInstance = new CurrentLocationXY();
        }
        return currentLocationXYInstance;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getX() {
        return this.x;
    }

    public String getY() {
        return this.y;
    }
}
