import { describe, expect, it } from "vitest";
import { InMemoryEventStore } from "../src/eventStore";
import type { TaskState } from "../src/task";
import type { TaskEvent } from "../src/taskEvents";
import { TaskProjector } from "../src/taskProjector";

const created = (id: string, title: string): TaskEvent => ({ type: "TaskCreated", inputData: { id, title }, timestamp: 0 });
const completed = (id: string): TaskEvent => ({ type: "TaskCompleted", inputData: { id }, timestamp: 0 });
const reopened = (id: string): TaskEvent => ({ type: "TaskReopened", inputData: { id }, timestamp: 0 });
const commented = (id: string, comment: string): TaskEvent => ({ type: "TaskCommentAdded", inputData: { id, comment }, timestamp: 0 });

// A frozen state throws a TypeError as soon as somebody tries to change it.
function frozen(state: TaskState): TaskState {
  for (const task of Object.values(state)) {
    Object.freeze(task.comments);
    Object.freeze(task);
  }
  return Object.freeze(state);
}

const projector = new TaskProjector();

describe("apply", () => {
  it("TaskCreated adds an open task without comments", () => {
    const state = projector.apply({}, created("t1", "Write report"));

    expect(state).toEqual({ t1: { id: "t1", title: "Write report", status: "open", comments: [] } });
  });

  it("TaskCompleted marks the task as completed", () => {
    const before = projector.apply({}, created("t1", "Write report"));

    const after = projector.apply(before, completed("t1"));

    expect(after.t1.status).toBe("completed");
  });

  it("TaskReopened opens a completed task again", () => {
    const before = projector.getCurrentState([created("t1", "Write report"), completed("t1")]);

    const after = projector.apply(before, reopened("t1"));

    expect(after.t1.status).toBe("open");
  });

  it("TaskCommentAdded appends comments in the order of the events", () => {
    const before = projector.getCurrentState([created("t1", "Write report"), commented("t1", "first")]);

    const after = projector.apply(before, commented("t1", "second"));

    expect(after.t1.comments).toEqual(["first", "second"]);
  });

  it("changes only the task named in the event", () => {
    const before = projector.getCurrentState([created("t1", "Write report"), created("t2", "Prepare slides")]);

    const after = projector.apply(before, completed("t2"));

    expect(after.t1.status).toBe("open");
    expect(after.t2.status).toBe("completed");
  });
});

describe("immutability", () => {
  const before = (): TaskState =>
    frozen({ t1: { id: "t1", title: "Write report", status: "open", comments: ["first"] } });

  it.each([
    ["TaskCreated", created("t2", "Prepare slides")],
    ["TaskCompleted", completed("t1")],
    ["TaskReopened", reopened("t1")],
    ["TaskCommentAdded", commented("t1", "second")],
  ])("%s returns a new state and leaves the old one untouched", (_type, event) => {
    const state = before();

    const after = projector.apply(state, event);

    expect(after).not.toBe(state);
    expect(state).toEqual(before());
  });
});

describe("events that do not fit", () => {
  const state = projector.apply({}, created("t1", "Write report"));

  it("ignores events for a task that does not exist", () => {
    expect(projector.apply(state, completed("missing"))).toEqual(state);
    expect(projector.apply(state, reopened("missing"))).toEqual(state);
    expect(projector.apply(state, commented("missing", "hello"))).toEqual(state);
  });

  it("ignores a second TaskCreated for the same id", () => {
    const after = projector.apply(state, created("t1", "Another title"));

    expect(after.t1.title).toBe("Write report");
  });

  it("ignores unknown event types", () => {
    const after = projector.apply(state, { type: "TaskDeleted", inputData: { id: "t1" }, timestamp: 0 });

    expect(after).toEqual(state);
  });
});

describe("getCurrentState", () => {
  it("returns an empty state when there are no events", () => {
    expect(projector.getCurrentState([])).toEqual({});
  });

  it("rebuilds the usage example from the sheet", () => {
    const store = new InMemoryEventStore();
    store.append({ type: "TaskCreated", inputData: { id: "t1", title: "Write report" }, timestamp: Date.now() });
    store.append({ type: "TaskCommentAdded", inputData: { id: "t1", comment: "Add introduction" }, timestamp: Date.now() });

    const state = projector.getCurrentState(store.getAll());

    expect(state).toEqual({
      t1: { id: "t1", title: "Write report", status: "open", comments: ["Add introduction"] },
    });
  });

  it("returns one state with all tasks, their status and their comments", () => {
    const events = [
      created("t1", "Write report"),
      commented("t1", "Add introduction"),
      created("t2", "Prepare slides"),
      completed("t1"),
      reopened("t1"),
      commented("t1", "Conclusion is missing"),
      completed("t2"),
    ];

    expect(projector.getCurrentState(events)).toEqual({
      t1: { id: "t1", title: "Write report", status: "open", comments: ["Add introduction", "Conclusion is missing"] },
      t2: { id: "t2", title: "Prepare slides", status: "completed", comments: [] },
    });
  });

  it("gives the same state every time the same events are replayed", () => {
    const events = [created("t1", "Write report"), completed("t1"), commented("t1", "done")];

    expect(projector.getCurrentState(events)).toEqual(projector.getCurrentState(events));
  });

  it("can rebuild an earlier state by replaying only the first events", () => {
    const events = [created("t1", "Write report"), completed("t1"), reopened("t1")];

    const afterTwoEvents = projector.getCurrentState(events.slice(0, 2));

    expect(afterTwoEvents.t1.status).toBe("completed");
  });
});
