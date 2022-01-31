package Java_Project;

import java.util.Date;
// Log.csv 파일의 칼럼 정보들을 저장하는 객체 클래스
// 각 칼럼에 맞는 필드 선언
public class Log {
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String gender;
    private Date signupDate;
    private Date lastLoginDate;
    private String lastLoginTime;
    private double point;

    public Log(String id, String firstName, String lastName, String email, String gender, Date signupDate, Date lastLoginDate, String lastLoginTime, double point) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.signupDate = signupDate;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
        this.point = point;
    }

    public Log(String id, String fullName, String email, String gender, Date signupDate, Date lastLoginDate, String lastLoginTime, double point) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.signupDate = signupDate;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
        this.point = point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    // firstName, lastName 필드를 합쳐서 fullName 필드 getter 생성
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // 남녀를 한글로 바꿔 출력하는 gender 필드 getter 생성
    public String getGender() {
        if (this.gender.equals("Male")) {
            return "남";
        } else if (this.gender.equals("Female")) {
            return "여";
        }
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", signupDate=" + signupDate +
                ", lastLoginDate=" + lastLoginDate +
                ", lastLoginTime=" + lastLoginTime +
                ", point=" + point +
                '}';
    }
}