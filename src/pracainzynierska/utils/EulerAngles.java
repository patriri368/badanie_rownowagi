package pracainzynierska.utils;

public class EulerAngles {

    private Double eulerX;
    private Double eulerY;
    private Double eulerZ;

    public EulerAngles(Double eulerX, Double eulerY, Double eulerZ) {
        this.eulerX = eulerX;
        this.eulerY = eulerY;
        this.eulerZ = eulerZ;
    }

    public Double getEulerX() {
        return eulerX;
    }

    public Double getEulerY() {
        return eulerY;
    }

    public Double getEulerZ() {
        return eulerZ;
    }
}

