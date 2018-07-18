package com.liaobd.common.base.model;

/**
 * 描述:
 * 作者: su
 * 日期: 2017/12/4 14:48
 */

public class Permission {
    public final String name;
    public final boolean granted;
    public final boolean shouldShowRequestPermissionRationale;


    public Permission(String name) {
        this(name, false,
                false);
    }

    public Permission(String name, boolean granted) {
        this(name, granted,
                false);
    }

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Permission that = (Permission) o;
            return this.granted == that.granted && (this.shouldShowRequestPermissionRationale
                    == that.shouldShowRequestPermissionRationale && this.name.equals(that.name));
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + (this.granted ? 1 : 0);
        result = 31 * result + (this.shouldShowRequestPermissionRationale ? 1 : 0);
        return result;
    }

    public String toString() {
        return "Permission{name=\'" + this.name + '\'' + ", granted=" + this.granted
                + ", shouldShowRequestPermissionRationale="
                + this.shouldShowRequestPermissionRationale + '}';
    }
}
