import type { Event } from "./eventStore";
import type { Projector } from "./projector";
import type { Task, TaskState } from "./task";
import type { TaskEvent } from "./taskEvents";

// Rebuilds the task state from events.
// No state is ever changed: every step returns a new object and leaves the old one as it was.
export class TaskProjector implements Projector<TaskState> {
  apply(state: TaskState, event: Event): TaskState {
    const taskEvent = event as TaskEvent;

    switch (taskEvent.type) {
      case "TaskCreated": {
        const { id, title } = taskEvent.inputData;
        return this.createTask(state, id, title);
      }
      case "TaskCompleted":
        return this.updateTask(state, taskEvent.inputData.id, (task) => ({ ...task, status: "completed" }));
      case "TaskReopened":
        return this.updateTask(state, taskEvent.inputData.id, (task) => ({ ...task, status: "open" }));
      case "TaskCommentAdded": {
        const { id, comment } = taskEvent.inputData;
        return this.updateTask(state, id, (task) => ({ ...task, comments: [...task.comments, comment] }));
      }
      default:
        // Unknown event types are ignored
        return state;
    }
  }

  getCurrentState(events: Event[]): TaskState {
    // Start with an empty state and apply the events one after the other
    const emptyState: TaskState = {};
    return events.reduce((state, event) => this.apply(state, event), emptyState);
  }

  private createTask(state: TaskState, id: string, title: string): TaskState {
    // A task is only created once
    if (state[id]) {
      return state;
    }
    return { ...state, [id]: { id, title, status: "open", comments: [] } };
  }

  private updateTask(state: TaskState, id: string, change: (task: Task) => Task): TaskState {
    // Events for tasks that do not exist are ignored
    const task = state[id];
    if (!task) {
      return state;
    }
    return { ...state, [id]: change(task) };
  }
}
