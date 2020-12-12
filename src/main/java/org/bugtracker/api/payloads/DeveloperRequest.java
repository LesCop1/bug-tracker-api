package org.bugtracker.api.payloads;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
