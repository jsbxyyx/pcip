package org.op;

public record Result(String address, long millisecond) {
    public String getAddress() {
        return address;
    }

    public long getMillisecond() {
        return millisecond;
    }
}
