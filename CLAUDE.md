# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

Coursework for the course "Software Design and Software Development Processes" (Universität Innsbruck, WS 2026, LVA 703501). It contains the weekly PS exercise sheets (`labs/sheet-NN/`) and the team term project (`project/`). Course slides are in `materials/` (gitignored).

## AI policy (binding, from `materials/ps/ps-01.pdf`, slide 14)

| Task | Status |
|---|---|
| Creative tasks: project idea and proposal, **written exercise answers**, project reports and reflection, pitches and slides | **Prohibited** |
| Professional core tasks: **programming, testing**, requirements specification, architectural design | **Prohibited** |
| Supporting tasks: literature search, explaining concepts and APIs, debugging hints, summarising and translating (without directly adopting the output) | Allowed |
| Presentation QA: spelling and grammar checks, editing text without changing its meaning, polishing diagrams and slides | Recommended |

Therefore, do not write solution code, tests, exercise answers, designs or report content for `labs/` or `project/`. Explain concepts, give debugging hints and proofread instead. Repo infrastructure (folder structure, build config, scripts) is fine. Exercise 1.3 on sheet 1 asks for an AI try-out where the prompt and the AI's answer are documented; that output is source material for the student, not text to submit.

## Submission rules (from the sheets)

- One zip per sheet, uploaded to OLAT. Exercises also have to be marked as done in OLAT.
- Code must be a project that can be imported straight into an IDE and run, and it must include a test suite that shows the solution is correct.
- Non-source files (answers, diagrams) must be `.png`, `.pdf` or `.txt`.
- Course languages: Java (default here), C# or C++. No PHP. Git remote preferably on `git.uibk.ac.at`.

## Layout and commands

- `labs/_template/` is a Maven project (Java 21, JUnit 5). `.\scripts\new-sheet.ps1 <n>` copies it to `labs/sheet-0n/` and sets the artifactId.
- `.\scripts\pack-sheet.ps1 <n>` builds `dist/sheet-0n.zip`, leaving out `sheet_*.pdf`, `target/` and IDE files.
- Each sheet is its own Maven project. Run `mvn test` inside the sheet folder, or `mvn test -Dtest=ClassName#method` for a single test. Maven is not installed system-wide; use the IDE's bundled Maven or install it.
- Term project deliverables: proposal (~2 pages, Oct 28/30), progress report (~10 pages, Dec 16/18), final report (~10 pages plus a 1-page individual reflection, Jan 27/29). Project management happens in Trello.
