package com.tatiana.api_usuarios.model;

import jakarta.persistence.*;

@Entity
public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private String email;

        @Version
        private Integer version;

        public Usuario() {}

        public Usuario(Long id, String nome, String email) {
            this.id = id;
            this.nome = nome;
            this.email = email;
        }
    public Integer getVersion() {
        return version;
    }

        public Long getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

