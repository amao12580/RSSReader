package per.rss.server.api.util.code;

public class SubClass extends SuperClass {

    private int mSubX = 1;

    public SubClass() {}

    @Override
    public void setX(int x) {
        super.setX(x);
        mSubX = x;
        System.out.println("SubX is assigned " + x);
    }

    public void printX() {
        System.out.println("SubX = " + mSubX);
    }
}