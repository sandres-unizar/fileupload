package com.example.fileupload;

import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class FileDbUsuarios {
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        private String id;

        private String name;

        private String Grupo;


        public FileDbUsuarios() {
        }

        public FileDbUsuarios(String name, String Grupo) {
            this.name = name;
            this.Grupo = Grupo;

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrupo() {
            return Grupo;
        }

        public void setGrupo(String Grupo) {
            this.Grupo = Grupo;
        }
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof FileDbUsuarios))
            return false;
        FileDbUsuarios fileDbUsuarios = (FileDbUsuarios) o;
        return Objects.equals(this.id, fileDbUsuarios.id) && Objects.equals(this.name, fileDbUsuarios.name)
                && Objects.equals(this.Grupo, fileDbUsuarios.Grupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.Grupo);
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + this.id + ", name='" + this.name + '\'' + ", Grupo='" + this.Grupo + '\'' + '}';
    }

}


