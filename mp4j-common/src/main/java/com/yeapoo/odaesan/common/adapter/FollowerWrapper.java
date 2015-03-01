package com.yeapoo.odaesan.common.adapter;

import com.yeapoo.odaesan.sdk.model.Follower;

public class FollowerWrapper {

    private Follower follower;
    private boolean ungrouped;

    public FollowerWrapper(Follower follower, boolean ungrouped) {
        this.follower = follower;
        this.ungrouped = ungrouped;
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

    public boolean isUngrouped() {
        return ungrouped;
    }

    public void setUngrouped(boolean ungrouped) {
        this.ungrouped = ungrouped;
    }
}
