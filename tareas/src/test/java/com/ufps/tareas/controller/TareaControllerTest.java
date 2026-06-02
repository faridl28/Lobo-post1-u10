package com.ufps.tareas.controller;

import com.ufps.tareas.entity.Tarea;
import com.ufps.tareas.service.TareaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TareaService service;

    @Test
    void get_tareaExiste_retorna200() throws Exception {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Test");
        when(service.buscarPorId(1L)).thenReturn(t);

        mockMvc.perform(get("/api/tareas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test"));
    }

    @Test
    void get_noExiste_retorna404() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new EntityNotFoundException("no encontrada"));

        mockMvc.perform(get("/api/tareas/99"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void post_tareaValida_retorna201() throws Exception {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Nueva");
        when(service.crear(any())).thenReturn(t);

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Nueva\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nueva"));
    }

    @Test
    void get_listarTodas_retorna200() throws Exception {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Lista");
        when(service.listarTodas()).thenReturn(List.of(t));

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Lista"));
    }

    @Test
    void patch_completar_retorna200() throws Exception {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Completada");
        t.setCompletada(true);
        when(service.completar(1L)).thenReturn(t);

        mockMvc.perform(patch("/api/tareas/1/completar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completada").value(true));
    }
}