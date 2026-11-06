import type { Event } from "./eventStore";

// The four event types of the task system, each with its own payload.
export interface TaskCreated extends Event<{ id: string; title: string }> {
  type: "TaskCreated";
}

export interface TaskCompleted extends Event<{ id: string }> {
  type: "TaskCompleted";
}

export interface TaskReopened extends Event<{ id: string }> {
  type: "TaskReopened";
}

export interface TaskCommentAdded extends Event<{ id: string; comment: string }> {
  type: "TaskCommentAdded";
}

export type TaskEvent = TaskCreated | TaskCompleted | TaskReopened | TaskCommentAdded;
