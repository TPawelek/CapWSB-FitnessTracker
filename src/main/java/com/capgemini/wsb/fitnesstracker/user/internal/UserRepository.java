package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Method searching users by partial email address using streams.
     *
     * @param email part of the email to search
     * @return list of users matching the partial email
     */
    default List<User> searchByPartialEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .toList();
    }

    /**
     * Retrieves a list of users whose birthdate is before the specified date.
     *
     * @param date the latest birthdate of the users to search for
     * @return a list of {@link User} objects whose birthdate is before the specified date,
     * or an empty list if no users match the search criteria
     */
    default List<User> searchUsersByAgeGreaterThan(LocalDate date) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .toList();
    }

}

