package az.azericard.serviceuser.domain.dto;

import az.azericard.serviceuser.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

import static az.azericard.serviceuser.constans.ValidationConstants.PASSWORD_MAX_LENGTH;
import static az.azericard.serviceuser.constans.ValidationConstants.PASSWORD_MIN_LENGTH;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    @NotBlank(message = "{field.notBlank}")
    private String firstName;

    @NotBlank(message = "{field.notBlank}")
    private String lastName;

    @NotBlank(message = "{field.notBlank}")
    private String username;

    @NotBlank(message = "{field.notBlank}")
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDto() {
    }

    public UserDto(User user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setUsername(user.getUsername());
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
