// Provided on the sheet.

// The domain model for a single task
export interface Task {
  id: string;
  title: string;
  status: "open" | "completed";
  comments: string[];
}

// The complete system state: all tasks, indexed by ID
export type TaskState = Record<string, Task>;
