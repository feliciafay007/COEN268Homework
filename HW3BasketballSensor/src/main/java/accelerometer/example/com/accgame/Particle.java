package accelerometer.example.com.accgame;

/**
 * Created by feliciafay on 5/10/15.
 */
public class Particle {
    /**
     * coefficient of restitution
     */
    private static final float COR = 0.7f;
    public float mPosX;
    public float mPosY;
    public float mVelX;
    public float mVelY;


    /**
     * test2
     * I think this "float dt = (System.nanoTime() - timestamp ) /  1000000000f;"
     * will be negative, it's not right.
     * use the acceleration values and timestamp to calculate
     * displacement of the particle along the X and Y axis.
     * does not work well
     *
     * nanoTime
     * nanoTime is significantly usually more accurate than currentTimeMillis
     * but it's a relatively expensive call as well.currentTimeMillis() runs
     * in a few (5-6) cpu clocks, nanoTime depends on the underlying architecture
     * and can be 100+ cpu clocks.
     */
    public void updatePosition(float sx, float sy, float sz, long timestamp) {
        float dt = (System.nanoTime() - timestamp ) /  1000000000f;
        long sysTime = System.nanoTime();
        dt = 0.4f;
        mVelX += -sx * dt;
        mVelY += -sy * dt;
        mPosX += mVelX * dt;
        mPosY += mVelY * dt;
        System.out.println("PosX = " + mPosX +  ", velX = " + mVelX + ", sensorX = " + (-sx) + ", t = " + dt);
        System.out.println("PosY = " + mPosY +  ", velY = " + mVelY + ", sensorY = " + (-sy) + ", t = " + dt);
    }




    /**
     * test1
     * use the acceleration values to calculate displacement of
     * the particle along the X and Y axis.
     */
//    public void updatePosition(float sx, float sy, float sz, long timestamp) {
//        long sysTime = System.nanoTime();
//        mVelX += -sx;
//        mVelY += -sy;
//        mPosX += mVelX;
//        mPosY += mVelY;
//        System.out.println("PosX = " + mPosX +  ", velX = " + mVelX + ", sensorX = " + (-sx));
//        System.out.println("PosY = " + mPosY +  ", velY = " + mVelY + ", sensorY = " + (-sy));
//    }


    /**
     * @param mHorizontalBound
     * @param mVerticalBound
     * add logic to create a counce effect when it collides with the boundary
     */
    public void resolveCollisionWithBounds (float mHorizontalBound, float mVerticalBound) {
        if (mPosX > mHorizontalBound) {
            mPosX = mHorizontalBound;
            mVelX = -mVelX * COR;
        } else if (mPosX < -mHorizontalBound) {
            mPosX = -mHorizontalBound;
            mVelX = -mVelX * COR;
        }
        if (mPosY > mVerticalBound) {
            mPosY = mVerticalBound;
            mVelY = -mVelY * COR;
        } else if (mPosY < -mVerticalBound) {
            mPosY = -mVerticalBound;
            mVelY = -mVelY * COR;
        }
    }
}
