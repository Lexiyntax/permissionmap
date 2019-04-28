package net.ashtech.permissionmap;

/**
 * Represents a time data point for a test
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
class TimeDataPoint {
    
    private String mapperName;
    private String setName;
    private long startTime;
    private long endTime;

    public TimeDataPoint(String mapperName, String setName, long startTime, long endTime) {
        this.mapperName = mapperName;
        this.setName = setName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * Computes and returns the time delta for this property
     * @return time delta
     */
    public long getTimeDelta() {
        return endTime - startTime;
    }
    
    /**
     * @return the mapperName
     */
    public String getMapperName() {
        return mapperName;
    }

    /**
     * @param mapperName the mapperName to set
     */
    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the setName
     */
    public String getSetName() {
        return setName;
    }

    /**
     * @param setName the setName to set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }
    
}
