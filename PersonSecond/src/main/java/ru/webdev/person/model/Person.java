package ru.webdev.person.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private String firstname;

    @NonNull
    private String surname;

    @NonNull
    private String lastname;

    @NonNull
    private LocalDate birthday;

    @NonNull
    private String location;

    public Person(@NonNull String firstname, @NonNull String surname, @NonNull String lastname, java.time.@NonNull LocalDate birthday, @NonNull String location) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.location = location;
    }
}
