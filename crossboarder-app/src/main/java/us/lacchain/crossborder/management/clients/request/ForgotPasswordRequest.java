package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ForgotPasswordRequest implements Serializable {
    @Email
    @NotNull(message="email can't be null")
    private String email;
}