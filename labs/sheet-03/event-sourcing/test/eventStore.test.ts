import { describe, expect, it } from "vitest";
import { InMemoryEventStore } from "../src/eventStore";

describe("InMemoryEventStore", () => {
  it("returns the events in the order they were appended", () => {
    const store = new InMemoryEventStore();
    store.append({ type: "TaskCreated", inputData: { id: "t1", title: "Write report" }, timestamp: 1 });
    store.append({ type: "TaskCompleted", inputData: { id: "t1" }, timestamp: 2 });

    expect(store.getAll().map((event) => event.type)).toEqual(["TaskCreated", "TaskCompleted"]);
  });

  it("is append-only: changing the returned array does not change the store", () => {
    const store = new InMemoryEventStore();
    store.append({ type: "TaskCreated", inputData: { id: "t1", title: "Write report" }, timestamp: 1 });

    store.getAll().pop();

    expect(store.getAll()).toHaveLength(1);
  });
});
