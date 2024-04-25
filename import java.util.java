import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;
    private String fullName;
    private String birthDate;
    private List<Address> addresses;

    public Person(String fullName, String birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.addresses = new ArrayList<>();
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
