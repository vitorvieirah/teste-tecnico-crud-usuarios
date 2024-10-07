package com.teste.usuario.entrypoint.controller.midlleware;

import com.teste.usuario.aplication.usecases.AutenticacaoUseCase;
import com.teste.usuario.builder.UsuarioBuilder;
import com.teste.usuario.infrastructure.dataprovider.exceptions.ErroDataProviderException;
import com.teste.usuario.infrastructure.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioHandlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository repository;

    @Autowired
    private AutenticacaoUseCase autenticacaoUseCase;

    private final UsuarioEntity usuarioTeste = UsuarioMapper.paraEntity(UsuarioBuilder.gerarUsuario());

    private final String USUARIO_JSON = UsuarioBuilder.gerarJsonUsuario();


    @Test
    void testeExceptionUsuarioJaCadastroComEmail() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.save(Mockito.any())).thenReturn(usuarioTeste);

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON));


        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());

        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("Usuário com este email já cadastrado"));
    }

    @Test
    void testeExceptionUsuarioNaoEncontrado() throws Exception {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("Usuário não encontrado"));
    }

    @Test
    void testeExceptionErroDataProviderExceptionSalvar() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any()))
                .thenThrow(new ErroDataProviderException(null, null));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("Erro ao salvar usuário."));
    }

    @Test
    void testeExceptionErroDataProviderExceptionConsultarPorId() throws Exception {
        Mockito.when(repository.findById(Mockito.any()))
                .thenThrow(new ErroDataProviderException(null, null));

        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));

        ResultActions resultadoRequisicao = mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("Erro ao consultar por id."));
    }

    @Test
    void testeExceptionErroDataProviderExceptionConsultarPorEmail() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any()))
                .thenThrow(new ErroDataProviderException(null, null));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USUARIO_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("Erro ao consultar por email."));
    }

    @Test
    void testeExceptionErroDataProviderExceptionDeletar() throws Exception {
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        Mockito.doThrow(new ErroDataProviderException(null, null))
                .when(repository).deleteById(Mockito.any());

        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("Erro ao deletar usuário."));
    }


    @Test
    void testeExceptionMethodArgumentNotValidSemNomeMetodoCadastrar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"\", \"email\":\"emailteste@gmail.com\", \"senha\":\"senhatesteA123@\"}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("O nome é obrigatório."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSemEmailMetodoCadastrar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"\", \"senha\":\"senhatesteA123@\"}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("O email é obrigatório."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSemSenhaMetodoCadastrar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": null}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha é obrigatória."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSenhaMenorQue6MetodoCadastrar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": \"aA2$\"}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha deve ter no mínimo 6 caracteres."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSenhaSemLetraMetodoCadastrar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": \"1234567\"}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha deve conter pelo menos uma letra, um número e um caractere especial."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSenhaSemCaracterEspecialMetodoCadastrar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": \"1234567asdh\"}";

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha deve conter pelo menos uma letra, um número e um caractere especial."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSemNomeMetodoAlterar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"\", \"email\":\"emailteste@gmail.com\", \"senha\":\"senhatesteA123@\"}";
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));

        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.put("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("O nome é obrigatório."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSemEmailMetodoAlterar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"\", \"senha\":\"senhatesteA123@\"}";
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.put("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("O email é obrigatório."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSemSenhaMetodoAlterar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": null}";
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.put("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha é obrigatória."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSenhaMenorQue6MetodoAlterar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": \"aA#1\"}";
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.put("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha deve ter no mínimo 6 caracteres."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSenhaSemLetraMetodoAlterar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": \"1234567\"}";
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.put("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha deve conter pelo menos uma letra, um número e um caractere especial."));

    }

    @Test
    void testeExceptionMethodArgumentNotValidSenhaSemCaracterEspecialMetodoAlterar() throws Exception {
        String usuarioJsonErroValidacao = "{\"nome\": \"User Test\", \"email\":\"emailteste@gmail.com\", \"senha\": \"1234567asdh\"}";
        String token = autenticacaoUseCase.gerarToken(UsuarioMapper.paraDomain(usuarioTeste));
        Mockito.when(repository.findByEmail(Mockito.any())).thenReturn(Optional.of(usuarioTeste));
        ResultActions resultadoRequisicao = mockMvc
                .perform(MockMvcRequestBuilders.put("/usuarios/{id}", usuarioTeste.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJsonErroValidacao));
        resultadoRequisicao.andExpect(MockMvcResultMatchers.status().isBadRequest());
        resultadoRequisicao.andExpect(MockMvcResultMatchers.jsonPath("$.erro.mensagens[0]").value("A senha deve conter pelo menos uma letra, um número e um caractere especial."));
    }


}
