public class WaterSample {
    //Attributes
    private String date;//0
    private String timestamp;//1
    private String location;//2
    private double temperature;//3
    private double humidity;//4
    private double sTemperature;//5
    private double pH;//6
    private long elecConductivity;//7
    private long totalDissolvedSols;//8
    private double totalSuspendedSols;//9
    private double dissolvedOxygen;//10
    private double turbidity;//11
    private double hardness;//12
    private String hardnessClass;//13

    //Constructor
    public WaterSample(String date, String timestamp, String location, double temperature, double humidity, double sTemperature, double pH, long elecConductivity, long totalDissolvedSols, double totalSuspendedSols, double dissolvedOxygen,double turbidity, double hardness, String hardnessClass) {
        this.date = date;
        this.timestamp = timestamp;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.sTemperature = sTemperature;
        this.pH = pH;
        this.elecConductivity = elecConductivity;
        this.totalDissolvedSols = totalDissolvedSols;
        this.totalSuspendedSols = totalSuspendedSols;
        this.dissolvedOxygen = dissolvedOxygen;
        this.turbidity = turbidity;
        this.hardness = hardness;
        this.hardnessClass = hardnessClass;
    }

    //Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSTemperature() {
        return sTemperature;
    }

    public void setSTemperature(double sTemperature) {
        this.sTemperature = sTemperature;
    }

    public double getPH() {
        return pH;
    }

    public void setPH(double pH) {
        this.pH = pH;
    }

    public long getElecConductivity() {
        return elecConductivity;
    }

    public void setElecConductivity(long elecConductivity) {
        this.elecConductivity = elecConductivity;
    }

    public long getTotalDissolvedSols() {
        return totalDissolvedSols;
    }

    public void setTotalDissolvedSols(long totalDissolvedSols) {
        this.totalDissolvedSols = totalDissolvedSols;
    }

    public double getTotalSuspendedSols() {
        return totalSuspendedSols;
    }

    public void setTotalSuspendedSols(double totalSuspendedSols) {
        this.totalSuspendedSols = totalSuspendedSols;
    }

    public double getDissolvedOxygen() {
        return dissolvedOxygen;
    }

    public void setDissolvedOxygen(double dissolvedOxygen) {
        this.dissolvedOxygen = dissolvedOxygen;
    }
    
    public double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
    }

    public double getHardness() {
        return hardness;
    }

    public void setHardness(double hardness) {
        this.hardness = hardness;
    }

    public String getHardnessClass() {
        return hardnessClass;
    }

    public void setHardnessClass(String hardnessClass) {
        this.hardnessClass = hardnessClass;
    }

    public void display(){
        String header = String.format("| %-10s | %-8s | %-18s | %-10.2f | %-6.2f | %-10.2f | %-10.2f | %-10d | %-10d | %-10.2f | %-10.2f | %-10.2f | %-10.2f | %-14s |", 
        date, timestamp, location, temperature, humidity, sTemperature, pH, elecConductivity, totalDissolvedSols,totalSuspendedSols, dissolvedOxygen, turbidity, hardness, hardnessClass);
        System.out.println(header);
    }
    
    public String toTXT() {
        return date + "," + timestamp + "," + location + "," + temperature + "," + humidity + "," + sTemperature + "," + pH + "," + elecConductivity + "," + totalDissolvedSols + "," + totalSuspendedSols + "," + dissolvedOxygen + "," + turbidity + "," + hardness + "," + hardnessClass;
    }
}