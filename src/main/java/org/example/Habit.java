package org.example;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class Habit {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Frequency frequency;
    private LocalDateTime createDate;
    private List<LocalDate> completionDates = new ArrayList<>();

    public Habit(Long id, Long userId, String name, String description, Frequency frequency, LocalDateTime createDate) {
        this.id = id;
        this.userId=userId;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", frequency=" + frequency +
                ", createDate=" + createDate +
                ", completionDates=" + completionDates +
                '}';
    }
}
