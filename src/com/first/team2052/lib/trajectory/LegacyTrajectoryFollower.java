package com.first.team2052.lib.trajectory;

public class LegacyTrajectoryFollower {
    private double kp_;
    private double kd_;
    private double kv_;
    private double ka_;
    private double last_error_;
    private double current_heading = 0;
    private int current_segment;
    private Trajectory profile_;
    public String name;

    public LegacyTrajectoryFollower(String name) {
        this.name = name;
    }

    public void configure(double kp, double kd, double kv, double ka) {
        kp_ = kp;
        kd_ = kd;
        kv_ = kv;
        ka_ = ka;
    }

    public void reset() {
        last_error_ = 0.0;
        current_segment = 0;
    }

    public void setTrajectory(Trajectory profile) {
        profile_ = profile;
    }

    public double calculate(double distance_so_far) {
        if (current_segment < profile_.getNumSegments()) {
            Trajectory.Segment segment = profile_.getSegment(current_segment);
            double error = segment.pos - distance_so_far;
            double output = kp_ * error + kd_ * ((error - last_error_)
                    / segment.dt - segment.vel) + (kv_ * segment.vel
                    + ka_ * segment.acc);

            last_error_ = error;
            current_heading = segment.heading;
            current_segment++;
            return output;
        } else {
            return 0;
        }
    }

    public double getHeading() {
        return current_heading;
    }

    public boolean isFinishedTrajectory() {
        return current_segment >= profile_.getNumSegments();
    }

    public Trajectory.Segment getCurrentSegment() {
        return profile_.getSegment(current_segment);
    }

    public int getCurrentSegmentNumber() {
        return current_segment;
    }

    public int getNumSegments() {
        return profile_.getNumSegments();
    }
}