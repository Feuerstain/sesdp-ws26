// Provided on the sheet.

// Generic event type
export interface Event<T = any> {
  type: string; // Type of event
  inputData: T; // Payload: what changed
  timestamp: number; // When it happened
}

// Simple event store interface
export interface EventStore {
  append(event: Event): void; // Add an event
  getAll(): Event[]; // All stored events
}

// Stores all events in memory (append-only)
export class InMemoryEventStore implements EventStore {
  private events: Event[] = [];

  append(event: Event) {
    this.events.push(event);
  }

  getAll(): Event[] {
    // Shallow copy prevents modification
    return [...this.events];
  }
}
