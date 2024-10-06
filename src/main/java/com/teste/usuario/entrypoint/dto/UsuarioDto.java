package com.teste.usuario.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UsuarioDto {
    @JsonProperty("id_usuario")
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).+$",
            message = "A senha deve conter pelo menos uma letra, um número e um caractere especial.")
    @JsonProperty("senha")
    private String senha;
    @JsonProperty("data_cadastro")
    private LocalDateTime dataCadastro;
    @JsonProperty("token")
    private String token;
}
