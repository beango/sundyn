package com.sundyn.vo;

public class AttendanceVo
{
    private int attendanceId;
    private int userId;
    private String attendDate;
    private String attendUp;
    private String attendDown;
    
    public int getAttendanceId() {
        return this.attendanceId;
    }
    
    public void setAttendanceId(final int attendanceId) {
        this.attendanceId = attendanceId;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public String getAttendDate() {
        return this.attendDate;
    }
    
    public void setAttendDate(final String attendDate) {
        this.attendDate = attendDate;
    }
    
    public String getAttendUp() {
        return this.attendUp;
    }
    
    public void setAttendUp(final String attendUp) {
        this.attendUp = attendUp;
    }
    
    public String getAttendDown() {
        return this.attendDown;
    }
    
    public void setAttendDown(final String attendDown) {
        this.attendDown = attendDown;
    }
}
