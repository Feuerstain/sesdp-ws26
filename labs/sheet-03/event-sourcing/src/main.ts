import { InMemoryEventStore } from "./eventStore";
import { TaskProjector } from "./taskProjector";

const store = new InMemoryEventStore();
const projector = new TaskProjector();

store.append({ type: "TaskCreated", inputData: { id: "t1", title: "Write report" }, timestamp: Date.now() });
store.append({ type: "TaskCommentAdded", inputData: { id: "t1", comment: "Add introduction" }, timestamp: Date.now() });
store.append({ type: "TaskCreated", inputData: { id: "t2", title: "Prepare slides" }, timestamp: Date.now() });
store.append({ type: "TaskCompleted", inputData: { id: "t1" }, timestamp: Date.now() });
store.append({ type: "TaskReopened", inputData: { id: "t1" }, timestamp: Date.now() });
store.append({ type: "TaskCommentAdded", inputData: { id: "t1", comment: "Conclusion is missing" }, timestamp: Date.now() });
store.append({ type: "TaskCompleted", inputData: { id: "t2" }, timestamp: Date.now() });

// Rebuild the complete state from all events
const state = projector.getCurrentState(store.getAll());
console.log(JSON.stringify(state, null, 2));
