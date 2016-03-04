package com.aniu.cnodejs_md.entity;

public class TopicUpInfo {

    public enum Action {
        up,
        down
    }

    private Action action;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
