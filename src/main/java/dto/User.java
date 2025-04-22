package dto;

import java.sql.Date;

public class User {
    private String name;           // 名前
    private String department;     // 部署
    private String position;       // 役職
    private String employmentType; // 雇用形態
    private String userId;         // ユーザーID
    private String password;       // パスワード

    // getter と setter を追加
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 新規登録に使われる User オブジェクトを生成するメソッド
    public static User createUserFromForm(String name, String department, String position, String employmentType, String userId, String password) {
        User user = new User();
        user.setName(name);
        user.setDepartment(department);
        user.setPosition(position);
        user.setEmploymentType(employmentType);
        user.setUserId(userId);
        user.setPassword(password);
        return user;
    }
}
