package dto;

public class EmployeeSearchDTO {
    private int employeeId;
    private String name;
    private String department;
    private String position;
    private String employmentType;
    private String userId;

    // Getters and setters for each field
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int i) {
        this.employeeId = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
