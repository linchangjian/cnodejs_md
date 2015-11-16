package com.aniu.cnodejs_md.entity;

import com.aniu.cnodejs_md.R;

public enum TabType {

    all(R.string.tab_all),

    good(R.string.tab_good),

    share(R.string.tab_share),

    ask(R.string.tab_ask),

    job(R.string.tab_job);

    private int nameId;

    TabType(int nameId) {
        this.nameId = nameId;
    }

    public int getNameId() {
        return nameId;
    }

}