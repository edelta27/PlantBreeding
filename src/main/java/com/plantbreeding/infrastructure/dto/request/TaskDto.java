package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.enumeration.TaskType;

import java.time.LocalDate;
import java.util.Objects;

public final class TaskDto {
    private final Long id;
    private final TaskType taskType;
    private final String notes;
    private final LocalDate taskDate;
    private final TaskStatus status;
    private final Long plantId;

    public TaskDto(Long id,
                   TaskType taskType,
                   String notes,
                   LocalDate taskDate,
                   TaskStatus status,
                   Long plantId) {
        this.id = id;
        this.taskType = taskType;
        this.notes = notes;
        this.taskDate = taskDate;
        this.status = status;
        this.plantId = plantId;
    }

    public Long id() {
        return id;
    }

    public TaskType taskType() {
        return taskType;
    }

    public String notes() {
        return notes;
    }

    public LocalDate taskDate() {
        return taskDate;
    }

    public TaskStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TaskDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.taskType, that.taskType) &&
                Objects.equals(this.notes, that.notes) &&
                Objects.equals(this.taskDate, that.taskDate) &&
                Objects.equals(this.status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskType, notes, taskDate, status);
    }

    @Override
    public String toString() {
        return "TaskDto[" +
                "id=" + id + ", " +
                "taskType=" + taskType + ", " +
                "notes=" + notes + ", " +
                "taskDate=" + taskDate + ", " +
                "status=" + status + ']';
    }

}