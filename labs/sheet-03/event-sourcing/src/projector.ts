// Provided on the sheet.
import type { Event } from "./eventStore";

// Projector that can rebuild state from events
export interface Projector<S> {
  // Apply one event to a given state
  apply(state: S, event: Event): S;
  // Rebuild entire state from events
  getCurrentState(events: Event[]): S;
}
